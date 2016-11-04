package tehnut.resourceful.crops2.util;

import com.google.common.base.Strings;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import tehnut.resourceful.crops2.ResourcefulCrops2;
import tehnut.resourceful.crops2.compat.ICompatibility;
import tehnut.resourceful.crops2.core.ConfigHandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AnnotationHelper {

    public static void loadCompatibilities(Set<ASMDataTable.ASMData> discovered) {
        for (ASMDataTable.ASMData data : discovered) {
            try {
                String modid = (String) data.getAnnotationInfo().get("modid");
                String configField = ((String) data.getAnnotationInfo().get("enabled"));
                ResourcefulCrops2.debug("Loading compatibility for plugin {}", modid);
                if (!Loader.isModLoaded(modid)) {
                    ResourcefulCrops2.debug("Failed to load compatibility for {}. Required mod is not installed.", modid);
                    continue;
                }
                if (!Strings.isNullOrEmpty(configField)) {
                    ConfigHandler.Compatibility compatConfig = ConfigHandler.compatibility;
                    Field compatField = compatConfig.getClass().getDeclaredField(configField);
                    if (compatField.getGenericType() == Boolean.TYPE && !compatField.getBoolean(ConfigHandler.compatibility)) {
                        ResourcefulCrops2.debug("Failed to load compatibility for {}. It is disabled in the config.", modid);
                        continue;
                    }
                }

                Class<?> asmClass = Class.forName(data.getClassName());
                Object compat = asmClass.newInstance();

                if (compat instanceof ICompatibility) {
                    ((ICompatibility) compat).loadCompatibility();
                    ResourcefulCrops2.debug("Loaded compatibility for plugin {}", modid);
                } else {
                    throw new RuntimeException("[" + ResourcefulCrops2.MODID + "] Class annoted with @Compatibility does not implement ICompatibility");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static <T> List<T> getAnnotationInstances(ASMDataTable dataTable, Class<Annotation> annotationClass, Class<T> typeClass) {
        List<T> widgetPlugins = new ArrayList<T>();
        Set<ASMDataTable.ASMData> discoveredPlugins = dataTable.getAll(annotationClass.getCanonicalName());

        for (ASMDataTable.ASMData data : discoveredPlugins) {
            try {
                Class<?> asmClass = Class.forName(data.getClassName());
                Class<? extends T> pluginClass = asmClass.asSubclass(typeClass);

                widgetPlugins.add(pluginClass.newInstance());
            } catch (Exception e) {
                ResourcefulCrops2.LOGGER.error("Error while handling annotation for class {}: {}", data.getClassName(), e.getLocalizedMessage());
            }
        }

        return widgetPlugins;
    }
}
package tehnut.resourceful.crops.block.prop;

import com.google.common.base.Optional;
import com.google.common.collect.Sets;
import net.minecraft.block.properties.PropertyHelper;
import tehnut.resourceful.crops.core.RegistrarResourcefulCrops;
import tehnut.resourceful.crops.core.data.Seed;

import java.util.Collection;
import java.util.Locale;
import java.util.Set;

public class PropertySeedType extends PropertyHelper<String> {

    public PropertySeedType() {
        super("seed_type", String.class);
    }

    @Override
    public Collection<String> getAllowedValues() {
        Set<String> allowed = Sets.newHashSet();
        for (Seed seed : RegistrarResourcefulCrops.SEEDS)
            if (seed.getRegistryName() != null && !RegistrarResourcefulCrops.SEED_DEFAULT.equals(seed.getRegistryName()))
                allowed.add(seed.getRegistryName().toString().replace(":", "_"));
        
        return allowed;
    }

    @Override
    public Optional<String> parseValue(String value) {
        return Optional.of(value);
    }

    @Override
    public String getName(String value) {
        return value.toLowerCase(Locale.ENGLISH);
    }
}

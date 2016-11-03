package tehnut.resourceful.crops.api.base;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tehnut.resourceful.crops.ConfigHandler;
import tehnut.resourceful.crops.api.ResourcefulAPI;

public enum Recipe {
    CHEST("SSS#S S#SSS"),TWOBYTWO("SS#SS"),THREEBYTHREE("SSS#SSS#SSS"),CROSS(" S #SSS# S ");
    
    private static final Pattern recipePattern = Pattern.compile("^[S #]{5,11}$");
    private static final Pattern linePattern   = Pattern.compile("^[S ]{2,3}$");
    private String recipeString;

    private Recipe(String recipeString) {
        this.recipeString = recipeString;
    }
    
    private String[] getAsArray() {
        return recipeString.split("#");
    }
    
    public static String[] parseRecipe(String recipeString) {
        if ("default".equals(recipeString)) {
            recipeString = ConfigHandler.defaultRecipe;
        }
        //Shortcut, as 2x2 is not a valid java literal
        if ("2x2".equals(recipeString)) {
            return TWOBYTWO.getAsArray();
        }
        //Shortcut, as 3x3 is not a valid java literal
        if ("3x3".equals(recipeString)) {
            return THREEBYTHREE.getAsArray();
        }
        if (valueOf(recipeString.toUpperCase()) != null) {
            return valueOf(recipeString.toUpperCase()).getAsArray();
        }
        if (recipePattern.matcher(recipeString).matches()) {
            String[] recipeArray = recipeString.split("#", 3);
            if (recipeArray.length == 2 || recipeArray.length == 3) {
                for (String line : recipeArray) {
                    Matcher m = linePattern.matcher(line);
                    if (!(m.matches() && line.length() == recipeArray.length)) {
                        ResourcefulAPI.logger.info("Illegal recipe found, using 'default': " + recipeString);
                        return parseRecipe("default");
                    }
                }
                return recipeArray;
            }
        }
        //Last resort
        return Recipe.CHEST.getAsArray();
    }
}

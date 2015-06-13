#Resourceful Crops

**Simply JSON based crops. No fluff, no problems.**

Uses only 2 Block ID's and 5 Item ID's. Everything else is done through NBT or Metadata.

##Example File

The current system will find any `.json` file in `../config/ResourcefulCrops/seeds` and attempt to read it. This can be useful for sorting.

Each file must start with a `seeds` field which contains a list of seeds. After that, specify as many seeds as you want in each file.

```
{
    "seeds": [
        {
            "name": "Example", // Sets the name to Example
            "tier": 2, // Sets the tier to 2: Magical
            "amount": 4, // Number of seeds to give when crafting them
            "input": "ingotIron", // Input OreDict entry
            "output": "minecraft:rotten_flesh:0#8", // Output ItemStack. Syntax is: domain:name:meta#amount
            "color": "#D0CBC7" // Color of the seed/crop/shard/pouch
        },
        {
            "name": "Example 2", // Sets the name to Example 2
            "tier": 1, // Sets the tier to 1: Mundane
            "amount": 8, // Number of seeds to give when crafting them
            "input": "minecraft:iron_ingot:0#0", // Input ItemStack. Syntax is: domain:name:meta#amount
            "output": "ingotGold#8", // Output OreDict entry. Syntax is: entry#amount
            "color": "#D0CBC7" // Color of the seed/crop/shard/pouch
        }
    ]
}
```

##Field Explanations

| Field    | Type   | Explanation                                                                                                                   |
|----------|--------|-------------------------------------------------------------------------------------------------------------------------------|
| `seeds`  | List   | List of seeds to add to the game                                                                                              |
| `name`   | String | Name of the seed. Gets run through the localization process, so if you have a resource loader, you can use unlocalized names. |
| `tier`   | int    | Tier of the seed. Determines how far into the game the user must be to craft that seed.                                       |
| `amount` | int    | Amount of seeds to return in a craft.                                                                                         |
| `input`  | String | OreDict entry or ItemStack of the item used to craft the seeds. See below for syntax.                                         |
| `output` | String | OreDict entry or ItemStack of the item returned from the shard crafting. See below for syntax.                                |
| `color`  | String | Hex color of the seeds. I suggest [this](http://www.color-hex.com/) page for getting your colors. Does require the "#".       |

###Custom ItemStack
ItemStacks determine the Amount and Meta of an item. The syntax is:

`domain:reg_name:meta#amount`

* domain
 * Generally the modid of the Block/Item. *EG: minecraft*
* reg_name 
 * The registered name of the Block/Item. *EG: wheat_seeds*
* meta
 * The meta data (or damage value) of the Block/Item. *EG: 0*
* amount
 * The amount of the item you want to return. For input, this does not matter, but is still required. *EG: 4*

###Custom OreDict
Using the OreDict for input/output is also viable.

**For input:**
`shapeType` *EG: `ingotIron`*

**For output:**
The only difference is defining the size of the output.
`shapeType#amount` *EG: `ingotIron#8`*

##Data Caching

A cache is kept to ensure that adding/removing crops does not mess with Seed/Shard/Pouch meta's. The cache is global, meaning across all worlds the user creates.

To reset this cache, delete `ResourcefulCropsCache.dat` from `../saves/`.
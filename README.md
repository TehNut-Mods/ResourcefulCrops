#Resourceful Crops

**Simply JSON based crops. No fluff, no problems.**

Uses only 2 Block ID's and 5 Item ID's. Everything else is done through NBT or Metadata.

##Example File

The current system will find any `.json` file in `../config/ResourcefulCrops/seeds` and attempt to read it. This can be useful for sorting.

Each file must start with a `seeds` field which contains a list of seeds. After that, specify as many seeds as you want in each file.

```json
{
    "seeds": [
        {
            "name": "Example",
            "tier": 2,
            "amount": 4,
            "input": "ingotIron",
            "output": "minecraft:rotten_flesh:0#8",
            "color": "#D0CBC7"
        },
        {
            "name": "Example 2",
            "tier": 1,
            "amount": 8,
            "input": "minecraft:iron_ingot:0#0",
            "output": "ingotGold#8",
            "color": "#D0CBC7"
        },
        {
            "name": "Example 3",
            "tier": 4,
            "amount": 6,
            "input": "minecraft:dirt:0#0",
            "output": "minecraft:stone:0#8",
            "color": "#D0CBC7",
            "seedReq": {
                "blockStack": {
                    "name": "minecraft:stone",
                    "meta": 0
                }
            }
        }
    ]
}
```

##Field Explanations

| Field    | Type   | Default      | Explanation                                                                                                                   |
|----------|--------|--------------|-------------------------------------------------------------------------------------------------------------------------------|
| `seeds`  | List   | **Required** | List of seeds to add to the game                                                                                              |
| `name`   | String | **Required** | Name of the seed. Gets run through the localization process, so if you have a resource loader, you can use unlocalized names. |
| `tier`   | int    | 1            | Tier of the seed. Determines how far into the game the user must be to craft that seed.                                       |
| `amount` | int    | 1            | Amount of seeds to return in a craft.                                                                                         |
| `input`  | String | **Required** | OreDict entry or ItemStack of the item used to craft the seeds. See below for syntax.                                         |
| `output` | String | **Required** | OreDict entry or ItemStack of the item returned from the shard crafting. See below for syntax.                                |
| `color`  | String | **Required** | Hex color of the seeds. I suggest [this](http://www.color-hex.com/) page for getting your colors. Does require the "#".       |

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

##Development Setup

This mod requires the following added to the workspace to work without issue:

* EnderIO (1.7.10)
* BloodMagic (1.7.10)
* MineFactoryReloaded (1.7.10)

To setup the workspace, simply fork/clone this repository and run `gradlew setupDecompWorkspace idea|eclipse`. 

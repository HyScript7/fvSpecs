execute as @e[tag=WellGenVoid] in fvspecs:voidrealm positioned as @s run fill ~2 ~12 ~2 ~-2 319 ~-2 deepslate_bricks
execute as @e[tag=WellGenVoid] in fvspecs:voidrealm positioned as @s run fill ~1 ~12 ~1 ~-1 319 ~-1 air
execute as @e[tag=WellGenVoid] in fvspecs:voidrealm positioned as @s run tag @s add WellGenVoidComplete
execute as @e[tag=WellGenVoid] in fvspecs:voidrealm positioned as @s run tag @s remove WellGenVoid

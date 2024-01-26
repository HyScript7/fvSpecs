execute as @e[tag=WellGen] in minecraft:overworld positioned as @s run fill ~2 ~-7 ~2 ~-2 -64 ~-2 deepslate_bricks
execute as @e[tag=WellGen] in minecraft:overworld positioned as @s run fill ~1 ~-7 ~1 ~-1 -64 ~-1 air
execute as @e[tag=WellGen] in minecraft:overworld positioned as @s run tag @s add WellGenComplete
execute as @e[tag=WellGen] in minecraft:overworld positioned as @s run tag @s remove WellGen

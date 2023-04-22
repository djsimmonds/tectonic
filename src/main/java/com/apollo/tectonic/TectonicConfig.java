package com.apollo.tectonic;

import net.minecraftforge.common.ForgeConfigSpec;

public class TectonicConfig {

	public static final ForgeConfigSpec GENERAL_SPEC;
	
	static {
	    ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
	    setupConfig(configBuilder);
	    GENERAL_SPEC = configBuilder.build();		
	}
	
	public static ForgeConfigSpec.ConfigValue<Boolean> LARGE_BIOMES_ENABLED;
	
	private static void setupConfig(ForgeConfigSpec.Builder builder) { 

		builder.push("Tectonic Mod Config");

		LARGE_BIOMES_ENABLED = builder
				.comment("Put \"true\" if you want large biomes to be enabled. Defaults to \"false\".")
				.define("Large Biomes Enabled", false);

		builder.pop();
	}
}

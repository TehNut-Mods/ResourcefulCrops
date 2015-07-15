package tehnut.resourceful.crops.compat.bloodmagic;

import WayofTime.alchemicalWizardry.api.harvest.HarvestRegistry;

public class CompatBloodMagic {

    static {
        HarvestRegistry.registerHarvestHandler(new ResourcefulCropsHarvestHandler());
    }

    public enum OrbType {

        WEAK(5000),
        APPRENTICE(25000),
        MAGICIAN(150000),
        MASTER(1000000),
        ARCHMAGE(10000000);

        private int capacity;

        OrbType(int capacity) {
            this.capacity = capacity;
        }

        public int getCapacity() {
            return capacity;
        }
    }
}

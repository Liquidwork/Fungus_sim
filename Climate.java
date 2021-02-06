/**
 * The abstract class climate will provide a method to give climate data for simulation
 */
public abstract class Climate {
    /**
     * It can be non-idempotent method providing different climate data for each day
     * After each call, an iteration can be done (selective).
     * @return an array of {tempature, humunity change}
     */
    public abstract double[] getClimate();

}

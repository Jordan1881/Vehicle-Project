public interface Loadable {
    double getMaxLoad();
    boolean canLoad(double weight);
    void load(double weight);
    double getCurrentLoad();
    void unload();

}
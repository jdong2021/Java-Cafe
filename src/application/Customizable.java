package application;

/**
 * A public interface to enforce add and remove methods on different classes
 *
 * @author Hugo De Moraes, Jonathan Dong
 */
public interface Customizable {
    /**
     * add method signature
     * @param obj
     * @return boolean
     */
    boolean add(Object obj);

    /**
     * remove method signature
     * @param obj
     * @return boolean
     */
    boolean remove(Object obj);
}

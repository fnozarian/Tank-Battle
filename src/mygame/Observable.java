package mygame;

/**
 *
 * @author MR Blue
 */
public interface Observable {

    /**
     * Adds an observer to the set of observers for this object, provided that
     * it is not the same as some observer already in the set. The order in
     * which notifications will be delivered to multiple observers is not
     * specified. See the class comment.
     *
     * @param o an observer to be added.
     */
    public void registerObserver(Observer o);

    /**
     * Deletes an observer from the set of observers of this object. Passing
     * <CODE>null</CODE> to this method will have no effect.
     *
     * @param o the observer to be deleted.
     */
    public void removeObserver(Observer o);

    /**
     * If this object has changed, as indicated by the
     * <code>hasChanged</code> method, then notify all of its observers and then
     * call the
     * <code>clearChanged</code> method to indicate that this object has no
     * longer changed.
     * <p>
     * Each observer has its
     * <code>update</code> method called with two arguments: this observable
     * object and
     * <code>null</code>.
     */
    public void notifyObserver(Object arg);

    /**
     * Marks this <tt>Observable</tt> object as having been changed; the
     * <tt>hasChanged</tt> method will now return <tt>true</tt>.
     */
    public void setChanged();

    /**
     * Indicates that this object has no longer changed, or that it has already
     * notified all of its observers of its most recent change, so that the
     * <tt>hasChanged</tt> method will now return <tt>false</tt>. This method is
     * called automatically by the
     * <code>notifyObservers</code> methods.
     *
     */
    public void clearChanged();

    /**
     * Tests if this object has changed.
     *
     * @return  <code>true</code> if and only if the <code>setChanged</code>
     * method has been called more recently than the <code>clearChanged</code>
     * method on this object; <code>false</code> otherwise.
     */
    public boolean hasChanged();
}

package jep.example;

/**
 * This interface is used to allow the collection of {@link Example}-instances and additional
 * elements in one collection.
 *
 */
public interface ExampleContent {

    /**
     * Returns the bundle key for the name of this example.
     * 
     * @return
     */
    String getBundleKey();

}

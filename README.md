# html-serializer [practicode-2](https://learn.malkabruk.co.il/practicode/projects/pract-2/)

### **Html Serializer and Querying**

### **Project Description**

The project focuses on building a tool that:

1. Retrieves HTML content from a given URL.
2. Parses the HTML content into a tree structure.
3. Enables basic querying functionality (similar to selectors used in web crawling and scraping).

---

### **New Things I Learned**

1. **Regular Expressions**:

   - Extracting HTML tags and plain text efficiently.
   - Managing edge cases like void tags and self-closing tags.

2. **Environment Variables**:

   - Using environment variables for dynamic values in development, staging, and production environments.

3. **Tree Structure Manipulation**:

   - Techniques for traversing and modifying the HTML tree structure.

4. **Asynchronous HTTP Requests with Java Futures**:

   - Implementing non-blocking HTTP requests using Java Futures.
   - Handling responses and exceptions in an asynchronous manner.

5. **Thread-Safe Singleton Pattern**:

   - Implementing `HtmlHelper` as a Singleton for shared state and to avoid repeated file parsing.

6. **Laziness in Iteration**:

   - Understanding the difference between `yield` in C# (`IEnumerable`) and Java's `Iterator` or custom solutions for lazy evaluation.

7. **Client-Side Rendering (CSR)**:

   - Realizing its impact on crawlers by observing missing elements that JavaScript dynamically adds to the DOM.




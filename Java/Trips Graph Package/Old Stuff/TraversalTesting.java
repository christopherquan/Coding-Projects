package graph;

import org.junit.Test;
import static org.junit.Assert.*;
import static java.util.Arrays.asList;
import java.util.ArrayList;

/** Unit tests for the Traversal class.
 *  @author Christopher Quan
 */
public class TraversalTesting {


    /** Class that extends BreadthFirstTraversal and creates
     *  list of the order in which vertices were visited. */
    class OrderBFT extends BreadthFirstTraversal {
        
        /** Create a new orderBFT. */
        protected OrderBFT(Graph G) {
            super(G);
            order = new ArrayList<Integer>();
        }

        /** Return order of Graph. */
        private ArrayList<Integer> returnOrder() {
            return order;
        }

        @Override
        protected boolean visit(int v) {
            order.add(v);
            return true;
        }

        /** Arraylist ORDER storing normal traversal of graph. */
        private ArrayList<Integer> order;
    }

    /** Class that extends DepthFirstTraversal and creates
     *  list of the order in which vertices were visited. */
    class OrderDFT extends DepthFirstTraversal {
        
        /** Create a new orderDFT. */
        protected OrderDFT(Graph G) {
            super(G);
            preorder = new ArrayList<Integer>();
            postorder = new ArrayList<Integer>();
        }

        /** Return preorder of Graph. */
        private ArrayList<Integer> returnPreorder() {
            return preorder;
        }

        /** Return postorder of Graph. */
        private ArrayList<Integer> returnPostorder() {
            return postorder;
        }

        @Override
        protected boolean visit(int v) {
            preorder.add(v);
            return true;
        }

        @Override
        protected boolean postVisit(int v) {
            postorder.add(v);
            return true;
        }

        @Override
        protected boolean shouldPostVisit(int v) {
            return true;
        }
        
        /** Arraylist PREORDER storing preorder traversal of graph. */
        private ArrayList<Integer> preorder;

        /** Arraylist POSTORDER storing postorder traversal of graph. */
        private ArrayList<Integer> postorder;
    }

    @Test
    public void testTraversal() {
        DirectedGraph g = new DirectedGraph();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add(5, 4);
        g.add(5, 3);
        g.add(4, 1);
        g.add(3, 2);
        g.add(1, 5);
        OrderBFT bft = new OrderBFT(g);
        bft.traverse(5);
        ArrayList<Integer> ord = bft.returnOrder();
        assertTrue(ord.equals(asList(5, 4, 3, 1, 2))
            || ord.equals(asList(5, 3, 4, 1, 2))
            || ord.equals(asList(5, 4, 3, 2, 1))
            || ord.equals(asList(5, 3, 4, 2, 1)));
        OrderDFT dft = new OrderDFT(g);
        dft.traverse(5);
        ArrayList<Integer> pre = dft.returnPreorder();
        ArrayList<Integer> post = dft.returnPostorder();
        assertTrue(pre.equals(asList(5, 4, 1, 3, 2))
            || pre.equals(asList(5, 3, 2, 4, 1)));
        assertTrue(post.equals(asList(1, 4, 2, 3, 5))
            || post.equals(asList(2, 3, 1, 4, 5)));

    }

}

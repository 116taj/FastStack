package comp2402a4;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
//COMP 2402 F2022 Assignment 4 solution by Taj Randhawa
//all O(log n) is base 2
public class FastSparrow implements RevengeOfSparrow {
  //Arrays have the fun fact that they auto init to 0 
  //Which happens to be our null value
  //This makes resizing sooooooooooooo much easier and faster
  int max[];
  long sum[];
  int n;
  int h;

  //constructor
  //i init the tree with 1 item and do prefix n addition so I can check if we need to expand the tree before adding
  public FastSparrow() {
    n = 0;
    h = 0;
    max = new int [1];
    sum = new long [1];
  }

  //push onto the stack
  //resize: O(n) amortized
  //normally O(2log n)
  public void push(int x) {
    //increment n to update our size
    n++;
    //if n is bigger than 2^h (including the new element)
    if (n > (1<<h)){
      //increment height and begin the resize process
      h++;
      //since our height is bigger now, we want to start at the second last row (last row is empty)
      int j = h-1;
      //declare new arrays with updated lengths
      int newMax[] = new int[(1<<h+1)-1];
      long newSum[] = new long[(1<<h+1)-1];
      //starting at the rightmost element on the 2nd last row
      for (int i = (1<<h)-2; i >= 0; i--){
        //place it into the next row 
        newMax[(1<<j)+i] = max[i];
        newSum[(1<<j)+i] = sum[i];
        //if we have reached all the data points, ascend to continue
        if (i < (1<<j))
          j--;
      }
      //copy the new arrays into the old ones
      max = newMax;
      sum = newSum;
      //handle the root case
      max[0] = max[1];
      sum[0] = 0;
    }
    //push the new element and update our tree accordingly
    max[(1<<h)+n-2] = x;
    sum[(1<<h)+n-2] = x;
    setMax((1<<h)+n-2,0);
    updateSum((1<<h)+n-2);
  }

  //removes an element from the stack (and trees)  
  //O(2log n) due to function calls
  public Integer pop() {
    if (n <= 0)
      return null; 
    //get the item from our tree (we need the value to remove it from max)
    int popped = max[(1<<h)+n-2];
    //reset our items  
    max[(1<<h)+n-2] = 0;
    sum[(1<<h)+n-2] = 0;
    //update
    setMax((1<<h)+n-2, popped);
    updateSum((1<<h)+n-2); 
    //decrement n and return the popped element
    n--;  
    return popped;
  }

  //get element
  //O(1)
  public Integer get(int i) {
    if (0 > i || i >= n)
      return null;
    return max[(1<<h)+i-1];
  }

  //set an element
  //O(2log n) worst case
  public Integer set(int i, int x) {
    if (0 > i || i >= n)
      return null;
    //store the old element
    int removed = max[(1<<h)+i-1];
    max[(1<<h)+i-1] = x;
    sum[(1<<h)+i-1] = x;
    //set the values and update our tree
    setMax((1<<h)+i-1, removed);
    updateSum((1<<h)+i-1);
    return removed;
  }

  //max will always be the root so just return that
  public Integer max() {
    if (n == 0)
      return null;
    return max[0];
  }

  //ksum
  //consider the top of our stack begins at the right of the tree
  //this means that we should always include the sum of all the elements to the right 
  //and exclude the left ones
  //exclusion is easier, so let's do that
  //begin at leaf with ksum being the root node value and go up
  //if the node we're at is a right child, then subtract the silbling's sum from ksum
  //once we're at the root, return ksum
  //O(log n) runtime
  public long ksum(int k) {
    if (k <= 0 || n <= 0)
    return 0;
   else if (k >= n)
    return sum[0];
   int i = (1<<h)+n-k-1;
   long ksum = sum[0];
   while (i != 0){
    int parent = (i-1)>>1;
    int leftChild = parent*2+1;
    if (i == parent*2+2)
      ksum-=sum[leftChild];
    i = parent;  
    }
   return ksum;
  }

  //update the sum by starting at a leaf
  //and making the parent the sum of its children
  //ending at the root
  //O(log n) 
  public void updateSum(int i){
    while (i != 0){
      int child = i;
      int parent = (i-1)>>1;
      int otherChild = parent*2+2;
      if (i == parent*2+2)
        otherChild = parent*2+1;
      if (sum[parent] == sum[child] + sum[otherChild]){
        break;
      }
      sum[parent] = sum[child] + sum[otherChild];
      i = parent;
    }
  }
  //update the sum by starting at a leaf
  //and changing the parent if one of its children is greater
  //ending at the root
  //if m != 0, then reset mode is enabled
  //where the parent gets set to 0 (as this value was removed)
  //so children always replace
  //O(log n) but most of the time ends early
  public void setMax(int i, int m){
    while (i != 0){
      int child = i;
      int parent = (i-1)>>1;
      if (max[parent] == m && m != 0)
        max[parent] = 0;
      int otherChild = parent*2+2;
      if (i == parent*2+2)
        otherChild = parent*2+1;  
      if (max[parent] >= max[child] && max[parent] >= max[otherChild]){
        if (m == 0 || (max[parent] != m && max[parent] != 0))
          break;
      }
      if (max[child] > max[otherChild] && (max[child] > max[parent] || max[parent] == 0))    
        max[parent] = max[child];
      else if (max[otherChild] >  max[parent] || max[parent] == 0)
        max[parent] = max[otherChild];
      i = parent;
    } 
  }

  //O(1) 
  public int size() {
    return n;
  }

  //I don't understand iterators too well
  //but this works for some reason, I made it like a loop
  //i set i to be the leftmost node of the last node
  //stopping condition is the final node (<=)
  //upon returning an element, increment i using postfix
  public Iterator<Integer> iterator() {
    return new Iterator<Integer>() {
      int i = (1<<h)-1;
      int stop = (1<<h)+n-2;
      
      public Integer next(){
        return max[i++];
      }
      public boolean hasNext(){
        return i <= stop;
      }
    };
  }
}

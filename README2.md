# FastStack
FastSparrow.java is my code, the other files are to help understand what my code does.
SlowSparrow is the slower implementation given as a template. 

This Stack has operations get(i), max() in O(1) time and push(x), pop(), set(i,x), ksum(k) in O(log n). Push and pop are slower to enable ksum and max being efficient. 

Ksum gets the top k elements of the stack and sums them together. A stack 5,4,3,2,1 where 5 is the top, ksum(3) would return 12.
Max gets the maximum value in the stack, which is 5. 

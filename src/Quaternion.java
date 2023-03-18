/** Quaternions. Basic operations. */
public class Quaternion {

   // TODO!!! Your fields here!

   /** Constructor from four double values.
    * @param a real part
    * @param b imaginary part i
    * @param c imaginary part j
    * @param d imaginary part k
    */

   private double x0, x1, x2, x3;


   public Quaternion (double a, double b, double c, double d) {
      // TODO!!! Your constructor here
      this.x0 = a;
      this.x1 = b;
      this.x2 = c;
      this.x3 = d;
   }

   /** Real part of the quaternion.
    * @return real part
    */
   public double getRpart() {
      return x0; // TODO!!!
   }

   /** Imaginary part i of the quaternion. 
    * @return imaginary part i
    */
   public double getIpart() {
      return x1; // TODO!!!
   }

   /** Imaginary part j of the quaternion. 
    * @return imaginary part j
    */
   public double getJpart() {
      return x2; // TODO!!!
   }

   /** Imaginary part k of the quaternion. 
    * @return imaginary part k
    */
   public double getKpart() {
      return x3; // TODO!!!
   }

   /** Conversion of the quaternion to the string.
    * @return a string form of this quaternion: 
    * "a+bi+cj+dk"
    * (without any brackets)
    */
   @Override
   public String toString() {
      return x0 + "+" + x1 + "i" + "+" + x2 + "j" + "+" + x3 + "k";
      // TODO!!!
   }

   /** Conversion from the string to the quaternion. 
    * Reverse to <code>toString</code> method.
    * @throws IllegalArgumentException if string s does not represent 
    *     a quaternion (defined by the <code>toString</code> method)
    * @param s string of form produced by the <code>toString</code> method
    * @return a quaternion represented by string s
    */
   public static Quaternion valueOf (String s) {
      return null; // TODO!!!
   }

   /** Clone of the quaternion.
    * @return independent clone of <code>this</code>
    */
   @Override
   public Object clone() throws CloneNotSupportedException {
      return (Quaternion) super.clone();
   }

   /** Test whether the quaternion is zero. 
    * @return true, if the real part and all the imaginary parts are (close to) zero
    */
   public boolean isZero() {
      return false; // TODO!!!
   }

   /** Conjugate of the quaternion. Expressed by the formula 
    *     conjugate(a+bi+cj+dk) = a-bi-cj-dk
    * @return conjugate of <code>this</code>
    */
   public Quaternion conjugate() {
      return new Quaternion(x0, -x1, -x2, -x3); // TODO!!!
   }

   /** Opposite of the quaternion. Expressed by the formula 
    *    opposite(a+bi+cj+dk) = -a-bi-cj-dk
    * @return quaternion <code>-this</code>
    */
   public Quaternion opposite() {
      return new Quaternion(-x0, -x1, -x2, -x3); // TODO!!!
   }

   /** Sum of quaternions. Expressed by the formula 
    *    (a1+b1i+c1j+d1k) + (a2+b2i+c2j+d2k) = (a1+a2) + (b1+b2)i + (c1+c2)j + (d1+d2)k
    * @param q addend
    * @return quaternion <code>this+q</code>
    */
   public Quaternion plus (Quaternion q) {
      Quaternion a = this;
      return new Quaternion(a.x0+q.x0, a.x1+q.x1, a.x2+q.x2, a.x3+q.x3); // TODO!!!
   }

   /** Product of quaternions. Expressed by the formula
    *  (a1+b1i+c1j+d1k) * (a2+b2i+c2j+d2k) = (a1a2-b1b2-c1c2-d1d2) + (a1b2+b1a2+c1d2-d1c2)i +
    *  (a1c2-b1d2+c1a2+d1b2)j + (a1d2+b1c2-c1b2+d1a2)k
    * @param q factor
    * @return quaternion <code>this*q</code>
    */
   public Quaternion times (Quaternion q) {
      Quaternion a = this;

      double y0 = a.x0*q.x0 - a.x1*q.x1 - a.x2*q.x2 - a.x3*q.x3;
      double y1 = a.x0*q.x1 + a.x1*q.x0 + a.x2*q.x3 - a.x3*q.x2;
      double y2 = a.x0*q.x2 - a.x1*q.x3 + a.x2*q.x0 + a.x3*q.x1;
      double y3 = a.x0*q.x3 + a.x1*q.x2 - a.x2*q.x1 + a.x3*q.x0;
      return new Quaternion(y0, y1, y2, y3); // TODO!!!
   }

      /** Multiplication by a coefficient.
    * @param r coefficient
    * @return quaternion <code>this*r</code>
    */
   public Quaternion times (double r) {
      Quaternion a = this;
      return new Quaternion(a.x0*r, a.x1*r, a.x2*r,  a.x3*r); // TODO!!!
   }

   /** Inverse of the quaternion. Expressed by the formula
    *     1/(a+bi+cj+dk) = a/(a*a+b*b+c*c+d*d) + 
    *     ((-b)/(a*a+b*b+c*c+d*d))i + ((-c)/(a*a+b*b+c*c+d*d))j + ((-d)/(a*a+b*b+c*c+d*d))k
    * @return quaternion <code>1/this</code>
    */
   public Quaternion inverse() {
      Quaternion a = this;
      double y0 = a.x0/((a.x0*a.x0)+(a.x1*a.x1)+(a.x2*a.x2)+(a.x3*a.x3));
      double y1 = (-a.x1)/((a.x0*a.x0)+(a.x1*a.x1)+(a.x2*a.x2)+(a.x3*a.x3));
      double y2 = (-a.x2)/((a.x0*a.x0)+(a.x1*a.x1)+(a.x2*a.x2)+(a.x3*a.x3));
      double y3 = (-a.x3)/((a.x0*a.x0)+(a.x1*a.x1)+(a.x2*a.x2)+(a.x3*a.x3));
      return new Quaternion(y0, y1, y2, y3);
   }

   /** Difference of quaternions. Expressed as addition to the opposite.
    * @param q subtrahend
    * @return quaternion <code>this-q</code>
    */
   public Quaternion minus (Quaternion q) {
      if (!(q instanceof Quaternion)){
         throw new IllegalArgumentException("Given object is not a Quaternion");
      }
      else{
         Quaternion cur = this;
         return new Quaternion(-q.x0+cur.x0, -q.x1+cur.x1, -q.x2+cur.x2, -q.x3+cur.x3);
      }
   }

   /** Right quotient of quaternions. Expressed as multiplication to the inverse.
    * @param q (right) divisor
    * @return quaternion <code>this*inverse(q)</code>
    */
   public Quaternion divideByRight (Quaternion q) {
      return null; // TODO!!!
   }

   /** Left quotient of quaternions.
    * @param q (left) divisor
    * @return quaternion <code>inverse(q)*this</code>
    */
   public Quaternion divideByLeft (Quaternion q) {
      return null; // TODO!!!
   }
   
   /** Equality test of quaternions. Difference of equal numbers
    *     is (close to) zero.
    * @param qo second quaternion
    * @return logical value of the expression <code>this.equals(qo)</code>
    */
   @Override
   public boolean equals (Object qo) {
      if (!(qo instanceof Quaternion)){
         throw new IllegalArgumentException("The given object is not Quaternion");
      }
      else{
         Quaternion o = (Quaternion) qo;
         return x0 == o.x0 && x1 == o.x1 && x2 == o.x2 && x3 == o.x3;
      }

   }

   /** Dot product of quaternions. (p*conjugate(q) + q*conjugate(p))/2
    * @param q factor
    * @return dot product of this and q
    */
   public Quaternion dotMult (Quaternion q) {
      Quaternion p = this;
      return Quaternion((p*q.conjugate()+q*p.conjugate())/2); // TODO!!!
   }

   /** Integer hashCode has to be the same for equal objects.
    * @return hashcode
    */
   @Override
   public int hashCode() {
      return 0; // TODO!!!
   }

   /** Norm of the quaternion. Expressed by the formula 
    *     norm(a+bi+cj+dk) = Math.sqrt(a*a+b*b+c*c+d*d)
    * @return norm of <code>this</code> (norm is a real number)
    */
   public double norm() {
      return Math.sqrt((x0*x0)+(x1*x1)+(x2*x2)+(x3*x3)); // TODO!!!
   }

   /** Main method for testing purposes.
    * @paramarg command line parameters
    */
   public static void main (String[] args) {
      // TODO!!! Your example runs here
   }

}


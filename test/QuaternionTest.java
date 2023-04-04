import static org.junit.Assert.*;
import org.junit.Test;

/** Test.
 * @author Jaanus
 * @version 0.6
 */
public class QuaternionTest {

   /** double numbers less than DELTA are considered zero */
   public static final double DELTA = 0.000001;

   @Test (timeout=1000)
   public void testConstructor() {
      Quaternion k1 = new Quaternion (-5., -81., 7., -13.);
      assertNotNull ("constructor of -5-81i+7j-13k returns null", k1);
      assertFalse ("constructor of -5-81i+7j-13k returns zero", k1.isZero());
      Quaternion k2 = new Quaternion (0., 0., -1., 350.);
      assertNotNull ("constructor returns null", k2);
      assertFalse ("different numbers must be different", k1.getRpart() == k2.getRpart());
      Quaternion k3 = new Quaternion (0., 0., 0., 0.);
      assertNotNull ("zero is not null", k3);
   }

   @Test (timeout=1000)
   public void testGetReIms() {
      Quaternion k1 = new Quaternion (3., 7., -5., -2.);
      double r = k1.getRpart();
      assertEquals ("3+7i-5j-2k has wrong real part", 3., r, DELTA);
      r = k1.getIpart();
      assertEquals ("3+7i-5j-2k has wrong imaginary part i", 7., r, DELTA);
      r = k1.getJpart();
      assertEquals ("3+7i-5j-2k has wrong imaginary part j", -5., r, DELTA);
      r = k1.getKpart();
      assertEquals ("3+7i-5j-2k has wrong imaginary part k", -2., r, DELTA);
      k1 = new Quaternion (0., 0., 0., 0.);
      r = k1.getRpart();
      assertEquals ("zero has wrong real part", 0., r, DELTA);
   }

   @Test (timeout=1000)
   public void testToString() {
      String s = new Quaternion (1., 4., 5., 7.).toString();
      assertTrue ("String <" + s + "> does not represent 1+4i+5j+7k", 
         (s.indexOf('1') < s.indexOf('4')) && (s.indexOf('1') >= 0)
          && (s.indexOf('4') < s.indexOf('5')) && 
          (s.indexOf('5') < s.indexOf('7')));
      s = new Quaternion (-1., 5., 0., 0.).toString();
      assertTrue ("String <" + s + "> does not contain a minus", 
         s.indexOf('-') >= 0);
   } 

   @Test (timeout=1000)
   public void testValueOf() {
      Quaternion f = new Quaternion (2., 5., 7., 9.);
      assertEquals ("valueOf must read back what toString outputs. ",
         f, Quaternion.valueOf (f.toString()));
      f = new Quaternion (-17., 10., 5., 8.);
      assertEquals ("valueOf must read back what toString outputs. ",
         f, Quaternion.valueOf (f.toString()));
      f = new Quaternion (0., 0., 0., 0.);
      assertEquals ("valueOf must read back what toString outputs. ",
         f, Quaternion.valueOf (f.toString()));
      f = new Quaternion (3., -2., 1., 3.);
      assertEquals ("valueOf must read back what toString outputs. ",
         f, Quaternion.valueOf (f.toString()));
      f = new Quaternion (-3., 0., -5., 1.);
      assertEquals ("valueOf must read back what toString outputs. ",
         f, Quaternion.valueOf (f.toString()));
      f = new Quaternion (3., 0., 0., -1.);
      assertEquals ("valueOf must read back what toString outputs. ",
         f, Quaternion.valueOf (f.toString()));
      f = new Quaternion (-3., 0., 0., 0.);
      assertEquals ("valueOf must read back what toString outputs. ",
         f, Quaternion.valueOf (f.toString()));
      f = new Quaternion (-3., -1., -10., -30.);
      assertEquals ("valueOf must read back what toString outputs. ",
         f, Quaternion.valueOf (f.toString()));
   }

   @Test (timeout=1000)
   public void testEqualsClone() {
      Quaternion k1 = new Quaternion (3., 7., 5., 2.);
      Quaternion k2 = new Quaternion (3., -9., 5., 2.);
      Quaternion k3 = new Quaternion (3., 7., 5., 2.);
      assertTrue ("3+7i+5j+2k must be equal to 3+7i+5j+2k", k1.equals (k3));
      assertFalse("3+7i+5j+2k must not be equal to 3-9i+5j+2k", k1.equals (k2));
      Quaternion k4 = null;
      try {
         k4 = (Quaternion)k3.clone();
      } catch (CloneNotSupportedException e) {};
      assertTrue ("identical numbers must be equal", k1.equals(k1));
      assertTrue ("independent instances of the same number must be equal",
         k3.equals(k1));
      assertTrue ("clone must be equal to original", k3.equals(k4));
      assertFalse ("clone must not be identical to original", k3 == k4);
      assertTrue ("zero must satisfy isZero", 
         new Quaternion (0., 0., 0., 0.).isZero());
      assertTrue ("number created from parts must be equal to original", 
         k2.equals (new Quaternion (k2.getRpart(), 
            k2.getIpart(), k2.getJpart(), k2.getKpart())));
      Quaternion k5 = new Quaternion (3.000000000000001, 7.000000000000001, 
         5., 2.);
      assertTrue ("cannot compare real numbers using == operator", 
         k5.equals (k1));
   }

   @Test (timeout=1000)
   public void testIsZero() {
      Quaternion k1 = new Quaternion (0., 0., 0., 0.);
      assertTrue ("0+0i+0j+0k must be zero", k1.isZero());
      k1 = new Quaternion (0., 1., 0., 0.);
      assertFalse ("0+1i+0j+0k must not be zero", k1.isZero());
      k1 = new Quaternion (0., -0.1, 0., 0.);
      assertFalse ("0-0.1i+0j+0k must not be zero", k1.isZero());
      k1 = new Quaternion (3., 7., 5., 2.);
      Quaternion k5 = new Quaternion (3.000000000000001, 7.000000000000001,
         5., 2.);
      assertTrue ("cannot compare real numbers using == operator",
         k5.minus (k1).isZero());
   }

   @Test (timeout=1000)
   public void testClone() {
      Quaternion f1 = new Quaternion (2., 5., 3., 7.);
      Quaternion f2 = null;
      try {
         f2 = (Quaternion)f1.clone();
      } catch (CloneNotSupportedException e) {};
      assertNotSame ("clone must differ from original", f2, f1);
      assertEquals ("clone must be equal to original", f1, f2);
      assertEquals ("clone must be equal to original", f2, f1);
      f1 = f2.plus(f2);
      assertEquals ("clone must be independent from original",
         new Quaternion (2., 5., 3., 7.), f2);
      f1 = new Quaternion (0., 0., 0., 0.);
      try {
         f2 = (Quaternion)f1.clone();
      } catch (CloneNotSupportedException e) {};   
      assertNotSame ("clone must differ from original", f2, f1);
      assertEquals ("clone must be equal to original", f1, f2);
   }

   @Test (timeout=1000)
   public void testOpposite() {
      Quaternion f1 = new Quaternion (1., 6., 8., 10.);
      Quaternion f2 = f1.opposite();
      assertEquals ("Wrong opposite: 1+6i+8j+10k", new Quaternion (-1., -6., -8., -10.), f2);
      assertEquals ("Do not change the argument of opposite",
         new Quaternion (1., 6., 8., 10.), f1);
      f1 = new Quaternion (-4., -75., -45., -1.);
      f2 = f1.opposite();
      assertEquals ("Wrong opposite: -4-75i-45j-1k", new Quaternion (4., 75., 45., 1.), f2);
      f1 = new Quaternion (0., 0., 0., 0.);
      f2 = f1.opposite();
      assertEquals ("zero must be neutral to opposite", f1, f2);
   }

   @Test (timeout=1000)
   public void testConjugate() {
      Quaternion f1 = new Quaternion (1., 6., 7., 11.);
      Quaternion f2 = f1.conjugate();
      assertEquals ("Wrong conjugate: 1+6i+7j+11k", new Quaternion (1., -6., -7., -11.), f2);
      assertEquals ("Do not change the argument of conjugate",
         new Quaternion (1., 6., 7., 11.), f1);
      f1 = new Quaternion (-4., -75., -2., -3.);
      f2 = f1.conjugate();
      assertEquals ("Wrong conjugate: -4-75i-2j-3k", new Quaternion (-4., 75., 2., 3.), f2);
      f1 = new Quaternion (12340., 0., 0., 0.);
      f2 = f1.conjugate();
      assertEquals ("real number must be neutral to conjugate", f1, f2);
   }

   @Test (timeout=1000)
   public void testPlus() { 
      Quaternion f1 = new Quaternion (2., 5., 1., 3.);
      Quaternion f2 = new Quaternion (4., 15., 2., 7.);
      Quaternion sum = f1.plus(f2);
      assertEquals ("Wrong sum: <" + f1 + "> + <" + f2 + ">",
         new Quaternion (6., 20., 3., 10.), sum);
      assertEquals ("plus must not change the first argument", new Quaternion (2., 5., 1., 3.),
         f1);
      assertEquals ("plus must not change the second argument", new Quaternion (4., 15., 2., 7.),
         f2);
      sum = f1.plus (f1);
      assertEquals ("plus must not change the arguments!", new Quaternion (2., 5., 1., 3.),
         f1);
      f1 = new Quaternion (-1., 25., -2., 3.);
      f2 = new Quaternion (1., -25., 2., -3.);
      sum = f1.plus(f2);
      assertEquals ("Wrong sum: <" + f1 + "> + <" + f2 + ">",
         new Quaternion (0., 0., 0., 0.), sum);
      f1 = new Quaternion (1., -22., 3., -5.);
      f2 = new Quaternion (0., 0., 0., 0.);
      sum = f1.plus (f2);
      assertEquals ("Wrong sum: <" + f1 + "> + <" + f2 + ">",
         new Quaternion (1., -22., 3., -5.), sum);
      f1 = new Quaternion (0., 0., 0., 0.);
      f2 = new Quaternion (-1., 41., -6., 9.);
      sum = f1.plus (f2);
      assertEquals ("Wrong sum: <" + f1 + "> + <" + f2 + ">",
         new Quaternion (-1., 41., -6., 9.), sum);
      f1 = new Quaternion (-2., -5., -4., -7.);
      f2 = new Quaternion (-1., -6., -2., -3.);
      sum = f1.plus(f2);
      assertEquals ("Wrong sum: <" + f1 + "> + <" + f2 + ">",
         new Quaternion (-3., -11., -6., -10.), sum);
   }

   @Test (timeout=1000)
   public void testMinus() {
      Quaternion f1 = new Quaternion (2., 5., 1., 3.);
      Quaternion f2 = new Quaternion (4., 15., 2., 7.);
      Quaternion dif = f1.minus(f2);
      assertEquals ("Wrong difference: <" + f1 + "> - <" + f2 + ">",
         new Quaternion (-2., -10., -1., -4.), dif);
      assertEquals ("minus must not change the first argument", new Quaternion (2., 5., 1., 3.),
         f1);
      assertEquals ("minus must not change the second argument", new Quaternion (4., 15., 2., 7.),
         f2);
      dif = f1.minus (f1);
      assertEquals ("minus must not change the arguments!", new Quaternion (2., 5., 1., 3.),
         f1);
      assertEquals ("Wrong difference: <" + f1 + "> - <" + f1 + ">",
         new Quaternion (0., 0., 0., 0.), dif);
      f1 = new Quaternion (4., 25., 13., 8.);
      f2 = new Quaternion (1., 5., 7., 4.);
      dif = f1.minus(f2);
      assertEquals ("Wrong difference: <" + f1 + "> - <" + f2 + ">",
         new Quaternion (3., 20., 6., 4.), dif);
   }

   @Test (timeout=1000)
   public void testTimesQ() {
      Quaternion f1 = new Quaternion (2., 3., 5., 7.);
      Quaternion f2 = new Quaternion (19., 17., 13., 11.);
      Quaternion prd = f1.times (f2);
      assertEquals ("Wrong product: <" + f1 + "> * <" + f2 + ">",
         new Quaternion (-155., 55., 207., 109.), prd);
      assertEquals ("Do not change the first argument of times",
         new Quaternion (2., 3., 5., 7.), f1);
      assertEquals ("Do not change the second argument of times",
         new Quaternion (19., 17., 13., 11.), f2);
      prd = f2.times (f1);
      assertEquals ("Wrong product: <" + f2 + "> * <" + f1 + ">",
         new Quaternion (-155., 127., 35., 201.), prd);
      f1 = new Quaternion (0., 0., 0., 0.);
      f2 = new Quaternion (2., 3., 4., 5.);
      prd = f1.times (f2);
      assertEquals ("Wrong product: <" + f1 + "> * <" + f2 + ">",
         new Quaternion (0., 0., 0., 0.), prd);
      prd = f2.times (f1);
      assertEquals ("Wrong product: <" + f2 + "> * <" + f1 + ">",
         new Quaternion (0., 0., 0., 0.), prd);
      f1 = new Quaternion (1., 0., 0., 0.);
      f2 = new Quaternion (2., 3., 4., 5.);
      prd = f1.times (f2);
      assertEquals ("Wrong product: <" + f1 + "> * <" + f2 + ">",
         new Quaternion (2., 3., 4., 5.), prd);
      prd = f2.times (f1);
      assertEquals ("Wrong product: <" + f2 + "> * <" + f1 + ">",
         new Quaternion (2., 3., 4., 5.), prd);
   } 

   @Test (timeout=1000)
   public void testTimesR() {
      Quaternion f = new Quaternion (2., 3., 5., 7.);
      Quaternion prd = f.times (0.5);
      assertEquals ("Wrong product: <" + f + "> * 0.5",
         new Quaternion (1., 1.5, 2.5, 3.5), prd);
      assertEquals ("Do not change the first argument of times",
         new Quaternion (2., 3., 5., 7.), f);
      prd = f.times (-20.);
      assertEquals ("Wrong product: <" + f + "> * 20",                                               
         new Quaternion (-40., -60., -100., -140.), prd); 
   }

   @Test (timeout=1000)
   public void testDotMult() {
      Quaternion f1 = new Quaternion (1., 2., 3., 5.);
      Quaternion f2 = new Quaternion (4., 7., -1., -6.);
      Quaternion prd = f1.dotMult (f2);
      assertEquals ("Wrong dot product: <" + f1 + "> * <" + f2 + ">",
         new Quaternion (-15., 0., 0., 0.), prd);
      assertEquals ("Do not change the first argument of dotMult",
         new Quaternion (1., 2., 3., 5.), f1);
      assertEquals ("Do not change the second argument of dotMult",
         new Quaternion (4., 7., -1., -6.), f2);
      prd = f2.dotMult (f1);
      assertEquals ("Wrong dot product: <" + f2 + "> * <" + f1 + ">",
         new Quaternion (-15., 0., 0., 0.), prd);
      f1 = new Quaternion (0., 0., 0., 0.);
      f2 = new Quaternion (2., 3., 4., 5.);
      prd = f1.dotMult (f2);
      assertEquals ("Wrong dot product: <" + f1 + "> * <" + f2 + ">",
         new Quaternion (0., 0., 0., 0.), prd);
      prd = f2.dotMult (f1);
      assertEquals ("Wrong product: <" + f2 + "> * <" + f1 + ">",
         new Quaternion (0., 0., 0., 0.), prd);
      f1 = new Quaternion (0., 2., 2., -3.);
      f2 = new Quaternion (0., 4., 2., 4.);
      prd = f1.dotMult (f2);
      assertEquals ("Wrong dot product: <" + f1 + "> * <" + f2 + ">",
         new Quaternion (0., 0., 0., 0.), prd);
      prd = f2.dotMult (f1);
      assertEquals ("Wrong dot product: <" + f2 + "> * <" + f1 + ">",
         new Quaternion (0., 0., 0., 0.), prd);
   }

   @Test (timeout=1000)
   public void testNorm() {
      Quaternion k1 = new Quaternion (4., 3., 0., 0.);
      double mm = k1.norm();
      assertEquals ("wrong module for 4+3i+0j+0k", 5., mm, DELTA);
      k1 = new Quaternion (0., 0., 0., 0.);
      mm = k1.norm();
      assertEquals ("wrong module for zero", 0., mm, DELTA);
      k1 = new Quaternion (1, -1., 1., 1.);
      mm = k1.norm();
      assertEquals ("wrong module for 1-1i+1j+1k", 2., mm, DELTA);
      k1 = new Quaternion (0., 8., 0., 0.);
      mm = k1.norm();
      assertEquals ("wrong module for 0+8i+0j+0k", 8., mm, DELTA);
   }

   @Test (timeout=1000)
   public void testInverse() {
      Quaternion f1 = new Quaternion (0.0032, -0.0048, -0.0096, -0.0384);
      Quaternion f2 = f1.inverse();
      assertEquals ("Wrong inverse <"+ f1 +">", new Quaternion (2., 3., 6., 24.), f2);
      assertEquals ("Do not change the argument of inverse",
         new Quaternion (0.0032, -0.0048, -0.0096, -0.0384), f1);
      f1 = f2.inverse();
      assertEquals ("Wrong inverse: 2+3i+6j+24k", new Quaternion (0.0032, -0.0048, -0.0096, -0.0384),
         f1);
      f1 = new Quaternion (1., 0., 0., 0.);
      f2 = f1.inverse();
      assertEquals ("1 must be neutral to inverse", f1, f2);
   }

   @Test (expected=RuntimeException.class)
   public void testZeroInverse() {
      Quaternion f = new Quaternion (0., 0., 0., 0.);
      f.inverse();
   }

   @Test (timeout=1000)
   public void testDivideByRight() {
      Quaternion f1 = new Quaternion (0.25, 0.25, -0.75, 0.5);
      Quaternion f2 = new Quaternion (1., -2., -1., 2.);
      Quaternion qt = f1.divideByRight (f2);
      assertEquals ("Wrong quotient from divideByRight: <" + f1 + "> / <" + f2 + ">",
         new Quaternion (0.15, 0.175, 0.1, 0.175), qt);
      assertEquals ("Do not change the first argument of divideByRight",
         new Quaternion (0.25, 0.25, -0.75, 0.5), f1);
      assertEquals ("Do not change the second argument of divideByRight",
         new Quaternion (1., -2., -1., 2.), f2);
      qt = f1.divideByRight (f1);
      assertEquals ("Do not change the arguments of divideByRight",
         new Quaternion (0.25, 0.25, -0.75, 0.5), f1);
      assertEquals ("Wrong quotient from divideByRight: <" + f1 + "> / <" + f1 + ">",
         new Quaternion (1., 0., 0., 0.), qt);
      f1 = new Quaternion (1., -1., 1., 1.);
      f2 = new Quaternion (1., 1., -1., 1.);
      qt = f1.divideByRight (f2);
      assertEquals ("Wrong quotient from divideByRight: <" + f1 + "> / <" + f2 + ">",
         new Quaternion (0., -1., 0., 0.), qt);
      Quaternion k1 = new Quaternion (3.0, 7.0, 5.0, 8.0);
      Quaternion k2 = new Quaternion (3.000000000000001, 7.000000000000001,
         5.000000000000001, 8.000000000000001);
      assertTrue ("cannot compare real numbers using == operator",
         k1.equals (k2));
   }

   @Test (expected=RuntimeException.class)
   public void testDivideByRightByZero() {
      Quaternion f1 = new Quaternion (2., 3., 6., 24.);
      Quaternion f2 = new Quaternion (0., 0., 0., 0.);
      f1.divideByRight (f2);
   }

   @Test (timeout=1000)
   public void testDivideByLeft() {
      Quaternion f1 = new Quaternion (0.25, 0.25, -0.75, 0.5);
      Quaternion f2 = new Quaternion (1., -2., -1., 2.);
      Quaternion qt = f1.divideByLeft (f2);
      assertEquals ("Wrong quotient from divideByLeft: <" + f1 + "> / <" + f2 + ">",
         new Quaternion (0.15, -0.025, -0.2, -0.175), qt);
      assertEquals ("Do not change the first argument of divideByLeft",
         new Quaternion (0.25, 0.25, -0.75, 0.5), f1);
      assertEquals ("Do not change the second argument of divideByLeft",
         new Quaternion (1., -2., -1., 2.), f2);
      qt = f1.divideByLeft (f1);
      assertEquals ("Do not change the arguments of divideByLeft",
         new Quaternion (0.25, 0.25, -0.75, 0.5), f1);
      assertEquals ("Wrong quotient from divideByLeft: <" + f1 + "> / <" + f1 + ">",
         new Quaternion (1., 0., 0., 0.), qt);
      f1 = new Quaternion (1., -1., 1., 1.);
      f2 = new Quaternion (1., 1., -1., 1.);
      qt = f1.divideByLeft (f2);
      assertEquals ("Wrong quotient from divideByLeft: <" + f1 + "> / <" + f2 + ">",
         new Quaternion (0., 0., 1., 0.), qt);
      Quaternion k1 = new Quaternion (3.0, 7.0, 5.0, 8.0);
      Quaternion k2 = new Quaternion (3.000000000000001, 7.000000000000001,
         5.000000000000001, 8.000000000000001);
      assertTrue ("cannot compare real numbers using == operator",
         k1.equals (k2));
   }

   @Test (expected=RuntimeException.class)
   public void testDivideByLeftByZero() {
      Quaternion f1 = new Quaternion (2., 3., 6., 24.);
      Quaternion f2 = new Quaternion (0., 0., 0., 0.);
      f1.divideByLeft (f2);
   }

   boolean consistent1 (Quaternion c) {
      if (!(c.plus(c.opposite()).isZero()))
         return false;
      if (!(c.equals(c.opposite().opposite())))
         return false;
      if (!(c.minus(c).isZero()))
         return false;
      return true;
   } // consistent1

   boolean consistent2 (Quaternion c) {
      Quaternion yks = new Quaternion (1., 0., 0., 0.);
      if (!(c.divideByRight(c).equals(yks)))
         return false;
      if (!(c.divideByLeft(c).equals(yks)))                                                           
         return false;
      if (!(c.times(c.inverse()).equals(yks)))
         return false;
      if (!(c.inverse().times(c).equals(yks)))
         return false;
      if (!(yks.divideByRight(c.inverse()).equals(c)))
         return false;
      if (!(yks.divideByLeft(c.inverse()).equals(c)))
         return false;
      if (!(c.inverse().inverse().equals(c)))
         return false;
      return true;
   } // consistent2

   @Test (timeout=1000)
   public void testConsistencyPlusMinusOpposite() {
      assertTrue ("1+1i+1j+1k is not ok + -", consistent1 (new Quaternion (1., 1., 1., 1.)));
      assertTrue ("0+0i+0j+0k is not ok + -", consistent1 (new Quaternion (0., 0., 0., 0.)));
      assertTrue ("2-3i+6j+24k is not ok + -", consistent1 (new Quaternion (2., -3., 6., 24.)));
   }

   @Test (timeout=1000)
   public void testConsistencyTimesInverseDivide() {
      assertTrue ("1+1i+1j+1k is not ok * /", consistent2 (new Quaternion (1., 1., 1., 1.)));
      assertTrue ("2-3i+6j+24k is not ok * /", consistent2 (new Quaternion (2., -3., 6., 24.)));
   }

   @Test (timeout=1000)
   public void testHashCode() {
      Quaternion q1 = new Quaternion (1., 2., 3., 4.);
      int h1 = q1.hashCode();
      Quaternion q2 = new Quaternion (1., 2., 3., 4.);
      int h2 = q2.hashCode();
      Quaternion q3 = null;
      try {
         q3 = (Quaternion)q1.clone();
      } catch (CloneNotSupportedException e) {};
      int h3 = q3.hashCode();
      assertTrue ("hashCode has to be same for equal objects", h1 == h2);
      assertTrue ("hashCode has to be same for clone objects", h1 == h3);
      assertTrue ("hashCode has to be same for the same object", h1 == q1.hashCode());
      q2 = new Quaternion (0., 2., 3., 4.);
      h2 = q2.hashCode();
      q3 = new Quaternion (1., 0., 3., 4.);                                                            
      h3 = q2.hashCode();
      Quaternion q4 = new Quaternion (1., 2., 0., 4.);
      int h4 = q4.hashCode();
      Quaternion q5 = new Quaternion (1., 2., 3., 0.);                                                 
      int h5 = q5.hashCode();
      assertFalse ("hashCode does not depend on real part", h1 == h2);
      assertFalse ("hashCode does not depend on imaginary part i", h1 == h3);
      assertFalse ("hashCode does not depend on imaginary part j", h1 == h4); 
      assertFalse ("hashCode does not depend on imaginary part k", h1 == h5); 
   }

   @Test (timeout=1000)
   public void testHashCodeSymmetry() {
      Quaternion q1 = new Quaternion (1., 2., 3., 4.);
      int h1 = q1.hashCode();
      Quaternion q2 = new Quaternion (1., 2., 4., 3.);
      int h2 = q2.hashCode();
      assertFalse ("hashCode must not be symmetrical: " 
         + q1 + " and " + q2 + " both give " + h1,
         h1 == h2);
      q2 = new Quaternion (1., 3., 2., 4.);
      h2 = q2.hashCode();
      assertFalse ("hashCode must not be symmetrical: "
         + q1 + " and " + q2 + " both give " + h1,
         h1 == h2);
      q2 = new Quaternion (1., 3., 4., 2.);
      h2 = q2.hashCode();
      assertFalse ("hashCode must not be symmetrical: "
         + q1 + " and " + q2 + " both give " + h1,
         h1 == h2);
      q2 = new Quaternion (1., 4., 2., 3.);
      h2 = q2.hashCode();
      assertFalse ("hashCode must not be symmetrical: "
         + q1 + " and " + q2 + " both give " + h1,
         h1 == h2);
      q2 = new Quaternion (1., 4., 3., 2.);
      h2 = q2.hashCode();
      assertFalse ("hashCode must not be symmetrical: "
         + q1 + " and " + q2 + " both give " + h1,
         h1 == h2);
      q2 = new Quaternion (2., 1., 3., 4.);
      h2 = q2.hashCode();
      assertFalse ("hashCode must not be symmetrical: "
         + q1 + " and " + q2 + " both give " + h1,
         h1 == h2);
      q2 = new Quaternion (2., 1., 4., 3.);
      h2 = q2.hashCode();
      assertFalse ("hashCode must not be symmetrical: "
         + q1 + " and " + q2 + " both give " + h1,
         h1 == h2);
      q2 = new Quaternion (2., 3., 1., 4.);
      h2 = q2.hashCode();
      assertFalse ("hashCode must not be symmetrical: "
         + q1 + " and " + q2 + " both give " + h1,
         h1 == h2);
      q2 = new Quaternion (2., 3., 4., 1.);
      h2 = q2.hashCode();
      assertFalse ("hashCode must not be symmetrical: "
         + q1 + " and " + q2 + " both give " + h1,
         h1 == h2);
      q2 = new Quaternion (2., 4., 1., 3.);
      h2 = q2.hashCode();
      assertFalse ("hashCode must not be symmetrical: "
         + q1 + " and " + q2 + " both give " + h1,
         h1 == h2);
      q2 = new Quaternion (2., 4., 3., 1.);
      h2 = q2.hashCode();
      assertFalse ("hashCode must not be symmetrical: "
         + q1 + " and " + q2 + " both give " + h1,
         h1 == h2);
      q2 = new Quaternion (3., 1., 2., 4.);
      h2 = q2.hashCode();
      assertFalse ("hashCode must not be symmetrical: "
         + q1 + " and " + q2 + " both give " + h1,
         h1 == h2);
      q2 = new Quaternion (3., 1., 4., 2.);
      h2 = q2.hashCode();
      assertFalse ("hashCode must not be symmetrical: "
         + q1 + " and " + q2 + " both give " + h1,
         h1 == h2);
      q2 = new Quaternion (3., 2., 1., 4.);
      h2 = q2.hashCode();
      assertFalse ("hashCode must not be symmetrical: "
         + q1 + " and " + q2 + " both give " + h1,
         h1 == h2);
      q2 = new Quaternion (3., 2., 4., 1.);
      h2 = q2.hashCode();
      assertFalse ("hashCode must not be symmetrical: "
         + q1 + " and " + q2 + " both give " + h1,
         h1 == h2);
      q2 = new Quaternion (3., 4., 1., 2.);
      h2 = q2.hashCode();
      assertFalse ("hashCode must not be symmetrical: "
         + q1 + " and " + q2 + " both give " + h1,
         h1 == h2);
      q2 = new Quaternion (3., 4., 2., 1.);
      h2 = q2.hashCode();
      assertFalse ("hashCode must not be symmetrical: "
         + q1 + " and " + q2 + " both give " + h1,
         h1 == h2);
      q2 = new Quaternion (4., 1., 2., 3.);
      h2 = q2.hashCode();
      assertFalse ("hashCode must not be symmetrical: "
         + q1 + " and " + q2 + " both give " + h1,
         h1 == h2);
      q2 = new Quaternion (4., 1., 3., 2.);
      h2 = q2.hashCode();
      assertFalse ("hashCode must not be symmetrical: "
         + q1 + " and " + q2 + " both give " + h1,
         h1 == h2);
      q2 = new Quaternion (4., 2., 1., 3.);
      h2 = q2.hashCode();
      assertFalse ("hashCode must not be symmetrical: "
         + q1 + " and " + q2 + " both give " + h1,
         h1 == h2);
      q2 = new Quaternion (4., 2., 3., 1.);
      h2 = q2.hashCode();
      assertFalse ("hashCode must not be symmetrical: "
         + q1 + " and " + q2 + " both give " + h1,
         h1 == h2);
      q2 = new Quaternion (4., 3., 1., 2.);
      h2 = q2.hashCode();
      assertFalse ("hashCode must not be symmetrical: "
         + q1 + " and " + q2 + " both give " + h1,
         h1 == h2);
      q2 = new Quaternion (4., 3., 2., 1.);
      h2 = q2.hashCode();
      assertFalse ("hashCode must not be symmetrical: "
         + q1 + " and " + q2 + " both give " + h1,
         h1 == h2);
   }
   
    @Test(timeout = 1000)
   public void testPowerOfZero(){
      Quaternion q1 = new Quaternion(1., 2., 3., 4.);
      Quaternion right_q = new Quaternion(1, 0, 0, 0);
      assertEquals("Quaternion to power 0 should be 1", right_q, q1.pow(0));

   }
   @Test(timeout = 1000)
   public void testPowerOfOne(){
      Quaternion q1 = new Quaternion(1., 2., 3., 4.);
      assertEquals("Quaternion to power 1 should be the quaternion itself (but not identical)", q1, q1.pow(1));

   }
   @Test(timeout = 1000)
   public void testPowerOfMinusOne(){
      Quaternion q1 = new Quaternion(1., 2., 3., 4.);
      assertEquals("Quaternion to power -1 should be the inverse of the quaternion", q1.inverse(), q1.pow(-1));

   }
   @Test(timeout = 1000)
   public void testPositivePower(){
      Quaternion right_q = new Quaternion(668., -224., -336., -448.);
      Quaternion q1 = new Quaternion(1., 2., 3., 4.);
      assertEquals("Quaternion (1r, 2i, 3j, 4k) to the power 4 should be (668r, -224i, -336j, -448k)", right_q, q1.pow(4));
   }
   @Test(timeout = 1000)
   public void testNegativePower(){
      Quaternion right_q = new Quaternion(0.000825 , 0.000277, 0.000415, 0.000553);
      Quaternion q1 = new Quaternion(1., 2., 3., 4.);
      assertEquals("Quaternion (1r, 2i, 3j, 4k) to the power 4 should be (0.000825r, 0.000277i, 0.000415j, 0.000553k)", right_q, q1.pow(-4));

   }



}


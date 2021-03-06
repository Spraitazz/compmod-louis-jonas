/**
 * Computer Modelling, Exercise 3: 
 *
 * This code is to demonstrate the use of the velocity-Verlet algorithm
 * for the Particle1D class.
 *
 * This is a particularly simple example in 1D with a particle of mass m=1 
 * and an anharmonic restoring force, resulting from an anharmonic potential
 * V(x) = 0.5*fc2 * x^2 + 0.25*fc4 * x^4.
 *
 * @author A. Hermann
 * @author A. R. Turner
 * @version "10/2015"
 */

// IO package is rquired for file writing
import java.io.*;

public class Particle1DVerlet  {

    /*
     * As we are doing file IO we need to throw and exception
     */
    public static void main (String[] argv) throws IOException {

        // Open the output file
        String outFile = argv[0];
        PrintWriter output = new PrintWriter(new FileWriter(outFile));

        /*
         * In your Particle3D test codes your should read the initial
         * state from a file using a Scanner (as in previous checkpoints).
         *
         * In this simple example, we're using hardwired constants for now
         * since we're not going to want to change them.
         */
        // Particle constants
        double mass   = 1.0;

        // Initial position and velocity
        double pos = 0.0;
        double vel = 1.0;

        // Create the particle
        Particle1D p = new Particle1D(mass, pos, vel);

        // Force constants and initial force
        double fc2 = 1.0;
        double fc4 = 1.0;
        double force = -fc2 * pos - fc4 * Math.pow(pos,3);

        // Number of timesteps
        int numstep = 100;
        // Size of timestep
        double dt = 0.1;
        // Initial time
        double t = 0;


        /*
         * This is the start of the velocity Verlet algorithm
         */
        // Print the initial position to file
        output.printf("%10.5f %10.5f\n", t, p.getPosition());

        /*
         * The main loop
         */
        for (int i=0;i<numstep;i++){

            // Update the postion using current velocity and force
            p.leapPosition(dt,force);

            // Update forces based on new positions
            double force_new = -fc2 * p.getPosition() - fc4 * Math.pow(p.getPosition(),3);
            
            // Update the velocity, based on average of current and
            // new forces
            p.leapVelocity(dt,0.5*(force+force_new));

            // Update the force variable to the new value
            force = force_new;
            
            // Increase the time
            t = t + dt;

            // Print the current position to file
            output.printf("%10.5f %10.5f\n", t, p.getPosition());

        }

        // Close the output file
        output.close();
    }
}

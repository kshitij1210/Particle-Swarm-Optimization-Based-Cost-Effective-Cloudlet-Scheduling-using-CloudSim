package MultiSwarm;

/**
 *
 * @author Divyanshu Mishra 20155043
 */

import java.io.File;
import java.util.Scanner;


/*
 * 
 * Change the path to where CEC files are present
 * 2 locations in this file
 * 
 */

public class testfunc {
	final double INF = 1.0e99;
	final double EPS = 1.0e-14;
	final double E  = 2.7182818284590452353602874713526625;
	final double PI = 3.1415926535897932384626433832795029;
	
	double[] OShift,M,y,z,x_bound;
	int ini_flag=0,n_flag,func_flag;

	
	
	//double sphere_func (double[] , double , int , double[] ,double[] ,int ) /* Sphere */
	//double ellips_func (double[] , double , int , double[] ,double[] ,int ) /* Ellipsoidal */
	//double bent_cigar_func (double[] , double , int , double[] ,double[] ,int ) /* Bent_Cigar */
	//double discus_func (double[] , double , int , double[] ,double[] ,int ) /* Discus */
	//double dif_powers_func (double[] , double , int , double[] ,double[] ,int ) /* Different Powers */
	//double rosenbrock_func (double[] , double , int , double[] ,double[] ,int ) /* Rosenbrock's */
	//double schaffer_F7_func (double[] , double , int , double[] ,double[] ,int ) /* Schwefel's 1.2  */
	//double ackley_func (double[] , double , int , double[] ,double[] ,int ) /* Ackley's  */
	//double weierstrass_func (double[] , double , int , double[] ,double[] ,int ) /* Weierstrass's  */
	//double griewank_func (double[] , double , int , double[] ,double[] ,int ) /* Griewank's  */
	//double rastrigin_func (double[] , double , int , double[] ,double[] ,int ) /* Rastrigin's  */
	//double step_rastrigin_func (double[] , double , int , double[] ,double[] ,int ) /* Noncontinuous Rastrigin's  */
	//double schwefel_func (double[] , double , int , double[] ,double[] ,int ) /* Schwefel's  */
	//double katsuura_func (double[] , double , int , double[] ,double[] ,int ) /* Katsuura  */
	//double bi_rastrigin_func (double[] , double , int , double[] ,double[] ,int ) /* Lunacek Bi_rastrigin Function */
	//double grie_rosen_func (double[] , double , int , double[] ,double[] ,int ) /* Griewank-Rosenbrock  */
	//double escaffer6_func (double[] , double , int , double[] ,double[] ,int ) /* Expanded Scaffer¡¯s F6  */
	
	//double cf01 (double[] , double , int , double[] ,double[] ,int ) /* Composition Function 1 */
	//double cf02 (double[] , double , int , double[] ,double[] ,int ) /* Composition Function 2 */
	//double cf03 (double[] , double , int , double[] ,double[] ,int ) /* Composition Function 3 */
	//double cf04 (double[] , double , int , double[] ,double[] ,int ) /* Composition Function 4 */
	//double cf05 (double[] , double , int , double[] ,double[] ,int ) /* Composition Function 5 */
	//double cf06 (double[] , double , int , double[] ,double[] ,int ) /* Composition Function 6 */
	//double cf07 (double[] , double , int , double[] ,double[] ,int ) /* Composition Function 7 */
	//double cf08 (double[] , double , int , double[] ,double[] ,int ) /* Composition Function 8 */
	
	//void shiftfunc (double[] , double[] , int ,double[] )
	//void rotatefunc (double[] , double[] , int ,double[] )
	//void asyfunc (double[] , double[] , int , double )
	//void oszfunc (double[] , double[] , int )
	//double cf_cal(double[] , double , int , double[] ,double[] ,double[] ,double[] , int )
	
	
	
				
	public void test_func(double[] x, double[] f, int nx, int mx,int func_num)throws Exception{
		int cf_num=10,i;
		if (ini_flag==1) 
		{
			if ((n_flag!=nx)||(func_flag!=func_num)) /* check if nx or func_num are changed, reinitialization*/
			{
				ini_flag=0;
			}
		}
		if (ini_flag==0) /* initiailization*/
		{
			
			y=new double[nx];
			z=new double[nx];
			x_bound=new double[nx];
			for (i=0; i<nx; i++)
				x_bound[i]=100.0;

			if (!(nx==2||nx==5||nx==10||nx==20||nx==30||nx==40||nx==50||nx==60||nx==70||nx==80||nx==90||nx==100))
			{
				System.out.println("\nError: Test functions are only defined for D=2,5,10,20,30,40,50,60,70,80,90,100.");
			}
			/*
			 * Change the path in here as well
			 */
			File fpt = new File("C:\\Users\\admin\\Desktop\\java\\input_data\\M_D"+nx+".txt");//* Load M data *
			Scanner input = new Scanner(fpt);
			if (!fpt.exists())
			{
			    System.out.println("\n Error: Cannot open input file for reading ");
			}
			 
			M=new double[cf_num*nx*nx]; 
			
			for (i=0; i<cf_num*nx*nx; i++)
			{
					M[i]=input.nextDouble();
			}
			input.close();
			

			/*
			 * Change the path in here as well
			 */
			fpt=new File("C:\\Users\\admin\\Desktop\\java\\input_data\\shift_data.txt");
			input = new Scanner(fpt);
			if (!fpt.exists())
			{
				System.out.println("\n Error: Cannot open input file for reading ");
			}
			OShift=new double[nx*cf_num];
			for(i=0;i<cf_num*nx;i++)
			{
				OShift[i]=input.nextDouble();
			}
			input.close();
			
			n_flag=nx;
			func_flag=func_num;
			ini_flag=1;
			
		}

					
		double[] t = new double[nx];
				
		for (i = 0; i < mx; i++)
		{
			for (int j=0; j<nx; j++){
				t[j] = x[i*nx+j];
			}
			switch(func_num)
			{
			case 1:	
				f[i] = sphere_func(t,f[i],nx,OShift,M,1);
				f[i]+=-1400.0;
				break;
			case 2:	
				f[i] = ellips_func(t,f[i],nx,OShift,M,1);
				f[i]+=-1300.0;
				break;	
			case 3:	
				f[i]=bent_cigar_func(t,f[i],nx,OShift,M,1);
				f[i]+=-1200.0;
				break;
			case 4:	
				f[i]=discus_func(t,f[i],nx,OShift,M,1);
				f[i]+=-1100.0;
				break;
			case 5:
				f[i]=dif_powers_func(t,f[i],nx,OShift,M,0);
				f[i]+=-1000.0;
				break;
			case 6:
				f[i]=rosenbrock_func(t,f[i],nx,OShift,M,1);
				f[i]+=-900.0;
				break;
			case 7:	
				f[i]=schaffer_F7_func(t,f[i],nx,OShift,M,1);
				f[i]+=-800.0;
				break;
			case 8:	
				f[i]=ackley_func(t,f[i],nx,OShift,M,1);
				f[i]+=-700.0;
				break;
			case 9:	
				f[i]=weierstrass_func(t,f[i],nx,OShift,M,1);
				f[i]+=-600.0;
				break;
			case 10:	
				f[i]=griewank_func(t,f[i],nx,OShift,M,1);
				f[i]+=-500.0;
				break;
			case 11:	
				f[i]=rastrigin_func(t,f[i],nx,OShift,M,0);
				f[i]+=-400.0;
				break;
			case 12:	
				f[i]=rastrigin_func(t,f[i],nx,OShift,M,1);
				f[i]+=-300.0;
				break;
			case 13:	
				f[i]=step_rastrigin_func(t,f[i],nx,OShift,M,1);
				f[i]+=-200.0;
				break;
			case 14:	
				f[i]=schwefel_func(t,f[i],nx,OShift,M,0);
				f[i]+=-100.0;
				break;
			case 15:	
				f[i]=schwefel_func(t,f[i],nx,OShift,M,1);
				f[i]+=100.0;
				break;
			case 16:	
				f[i]=katsuura_func(t,f[i],nx,OShift,M,1);
				f[i]+=200.0;
				break;
			case 17:	
				f[i]=bi_rastrigin_func(t,f[i],nx,OShift,M,0);
				f[i]+=300.0;
				break;
			case 18:	
				f[i]=bi_rastrigin_func(t,f[i],nx,OShift,M,1);
				f[i]+=400.0;
				break;
			case 19:	
				f[i]=grie_rosen_func(t,f[i],nx,OShift,M,1);
				f[i]+=500.0;
				break;
			case 20:	
				f[i]=escaffer6_func(t,f[i],nx,OShift,M,1);
				f[i]+=600.0;
				break;
			case 21:	
				f[i]=cf01(t,f[i],nx,OShift,M,1);
				f[i]+=700.0;
				break;
			case 22:	
				f[i]=cf02(t,f[i],nx,OShift,M,0);
				f[i]+=800.0;
				break;
			case 23:	
				f[i]=cf03(t,f[i],nx,OShift,M,1);
				f[i]+=900.0;
				break;
			case 24:	
				f[i]=cf04(t,f[i],nx,OShift,M,1);
				f[i]+=1000.0;
				break;
			case 25:	
				f[i]=cf05(t,f[i],nx,OShift,M,1);
				f[i]+=1100.0;
				break;
			case 26:
				f[i]=cf06(t,f[i],nx,OShift,M,1);
				f[i]+=1200.0;
				break;
			case 27:
				f[i]=cf07(t,f[i],nx,OShift,M,1);
				f[i]+=1300.0;
				break;
			case 28:
				f[i]=cf08(t,f[i],nx,OShift,M,1);
				f[i]+=1400.0;
				break;
			default:
				System.out.println("\nError: There are only 28 test functions in this test suite!");
				f[i] = 0.0;
				break;
			}
			
		}

	}
	
	double sphere_func (double[] x, double f, int nx, double[] Os,double[] Mr,int r_flag) /* Sphere */
	{
		int i;
		shiftfunc(x, y, nx, Os);
		if (r_flag==1)
		rotatefunc(y, z, nx, Mr);
		else
	    for (i=0; i<nx; i++){
			z[i]=y[i];
		}
						
		f = 0.0;
	    for (i=0; i<nx; i++)
	    {
	    	f += z[i]*z[i];
	    }
	    
	    return f;
	}
	
	
	double ellips_func (double[] x, double f, int nx, double[] Os,double[] Mr,int r_flag) /* Ellipsoidal */
	{
	    int i;
		shiftfunc(x, y, nx, Os);
		if (r_flag==1)
		rotatefunc(y, z, nx, Mr);
		else
	    for (i=0; i<nx; i++)
			z[i]=y[i];
	    oszfunc (z, y, nx);
		f = 0.0;
	    for (i=0; i<nx; i++)
	    {
	        f += Math.pow(10.0,6.0*i/(nx-1))*y[i]*y[i];
	    }
	    return f;
	}
	
	
	double bent_cigar_func (double[] x, double f, int nx, double[] Os,double[] Mr,int r_flag) /* Bent_Cigar */
	{
	    int i;
		double beta=0.5;
		shiftfunc(x, y, nx, Os);
		if (r_flag==1)
		rotatefunc(y, z, nx, Mr);
		else
	    for (i=0; i<nx; i++)
			z[i]=y[i];
	    asyfunc (z, y, nx,beta);
		if (r_flag==1){
			double[] t = new double[nx*nx];
			for(i=0;i<nx*nx;i++){
				t[i] = Mr[nx*nx+i];
			}
			rotatefunc(y,z,nx,t);
		}
		else
	    for (i=0; i<nx; i++)
			z[i]=y[i];

		f = z[0]*z[0];
	    for (i=1; i<nx; i++)
	    {
	        f += Math.pow(10.0,6.0)*z[i]*z[i];
	    }
	    
	    return f;
	}
	
	
	double discus_func (double[] x, double f, int nx, double[] Os,double[] Mr,int r_flag) /* Discus */
	{
	    int i;
		shiftfunc(x, y, nx, Os);
		if (r_flag==1)
		rotatefunc(y, z, nx, Mr);
		else
	    for (i=0; i<nx; i++)
			z[i]=y[i];
	    oszfunc (z, y, nx);

		f = Math.pow(10.0,6.0)*y[0]*y[0];
	    for (i=1; i<nx; i++)
	    {
	        f += y[i]*y[i];
	    }
	    
	    return f;
	}
	
	
	double dif_powers_func (double[] x, double f, int nx, double[] Os,double[] Mr,int r_flag) /* Different Powers */
	{
		int i;
		shiftfunc(x, y, nx, Os);
		if (r_flag==1)
		rotatefunc(y, z, nx, Mr);
		else
	    for (i=0; i<nx; i++)
			z[i]=y[i];
		f = 0.0;
	    for (i=0; i<nx; i++)
	    {
	        f += Math.pow(Math.abs(z[i]),2+4*i/(nx-1));
	    }
		f=Math.pow(f,0.5);
		
		return f;
	}
	
	
	double rosenbrock_func (double[] x, double f, int nx, double[] Os,double[] Mr,int r_flag) /* Rosenbrock's */
	{
	    int i;
		double tmp1,tmp2;
		shiftfunc(x, y, nx, Os);//shift
		for (i=0; i<nx; i++)//shrink to the orginal search range
	    {
	        y[i]=y[i]*2.048/100;
	    }
		if (r_flag==1)
		rotatefunc(y, z, nx, Mr);//rotate
		else
	    for (i=0; i<nx; i++)
			z[i]=y[i];
		for (i=0; i<nx; i++)//shift to orgin
	    {
	        z[i]=z[i]+1;
	    }

	    f = 0.0;
	    for (i=0; i<nx-1; i++)
	    {
			tmp1=z[i]*z[i]-z[i+1];
			tmp2=z[i]-1.0;
	        f += 100.0*tmp1*tmp1 +tmp2*tmp2;
	    }
	    
	    return f;
	}
	
	
	double schaffer_F7_func (double[] x, double f, int nx, double[] Os,double[] Mr,int r_flag) /* Schwefel's 1.2  */
	{
	    int i;
		double tmp;
		shiftfunc(x, y, nx, Os);
		if (r_flag==1)
		rotatefunc(y, z, nx, Mr);
		else
	    for (i=0; i<nx; i++)
			z[i]=y[i];
		asyfunc (z, y, nx, 0.5);
		for (i=0; i<nx; i++)
			z[i] = y[i]*Math.pow(10.0,1.0*i/(nx-1)/2.0);
		if (r_flag==1){
			double[] t = new double[nx*nx];
			for(i=0;i<nx*nx;i++){
				t[i] = Mr[nx*nx+i];
			}
						rotatefunc(z, y, nx, t);
		}
		else
	    for (i=0; i<nx; i++)
			y[i]=z[i];

		for (i=0; i<nx-1; i++)
			z[i]=Math.pow(y[i]*y[i]+y[i+1]*y[i+1],0.5);
	    f = 0.0;
	    for (i=0; i<nx-1; i++)
	    {
		  tmp=Math.sin(50.0*Math.pow(z[i],0.2));
	      f += Math.pow(z[i],0.5)+Math.pow(z[i],0.5)*tmp*tmp ;
	    }
		f = f*f/(nx-1)/(nx-1);
		
		return f;
	}
	
	
	double ackley_func (double[] x, double f, int nx, double[] Os,double[] Mr,int r_flag) /* Ackley's  */
	{
	    int i;
	    double sum1, sum2;

		shiftfunc(x, y, nx, Os);
		if (r_flag==1)
		rotatefunc(y, z, nx, Mr);
		else
	    for (i=0; i<nx; i++)
			z[i]=y[i];

		asyfunc (z, y, nx, 0.5);
		for (i=0; i<nx; i++)
			z[i] = y[i]*Math.pow(10.0,1.0*i/(nx-1)/2.0);
		if (r_flag==1){
			double[] t = new double[nx*nx];
			for(i=0;i<nx*nx;i++){
				t[i] = Mr[nx*nx+i];
			}
						rotatefunc(z, y, nx, t);
		}
		else
	    for (i=0; i<nx; i++)
			y[i]=z[i];

	    sum1 = 0.0;
	    sum2 = 0.0;
	    for (i=0; i<nx; i++)
	    {
	        sum1 += y[i]*y[i];
	        sum2 += Math.cos(2.0*PI*y[i]);
	    }
	    sum1 = -0.2*Math.sqrt(sum1/nx);
	    sum2 /= nx;
	    f =  E - 20.0*Math.exp(sum1) - Math.exp(sum2) +20.0;
	    
	    return f;
	}
	
	
	double weierstrass_func (double[] x, double f, int nx, double[] Os,double[] Mr,int r_flag) /* Weierstrass's  */
	{
	    int i,j,k_max;
	    double sum,sum2=0, a, b;

		shiftfunc(x, y, nx, Os);
		for (i=0; i<nx; i++)//shrink to the orginal search range
	    {
	        y[i]=y[i]*0.5/100;
	    }
		if (r_flag==1)
		rotatefunc(y, z, nx, Mr);
		else
	    for (i=0; i<nx; i++)
			z[i]=y[i];

		asyfunc (z, y, nx, 0.5);
		for (i=0; i<nx; i++)
			z[i] = y[i]*Math.pow(10.0,1.0*i/(nx-1)/2.0);
		if (r_flag==1){
			double[] t = new double[nx*nx];
			for(i=0;i<nx*nx;i++){
				t[i] = Mr[nx*nx+i];
			}
						rotatefunc(z, y, nx, t);
		}
		else
	    for (i=0; i<nx; i++)
			y[i]=z[i];

	    a = 0.5;
	    b = 3.0;
	    k_max = 20;
	    f = 0.0;
	    for (i=0; i<nx; i++)
	    {
	        sum = 0.0;
			sum2 = 0.0;
	        for (j=0; j<=k_max; j++)
	        {
	            sum += Math.pow(a,j)*Math.cos(2.0*PI*Math.pow(b,j)*(y[i]+0.5));
				sum2 += Math.pow(a,j)*Math.cos(2.0*PI*Math.pow(b,j)*0.5);
	        }
	        f += sum;
	    }
		f -= nx*sum2;
		
		return f;
	}
	
	
	double griewank_func (double[] x, double f, int nx, double[] Os,double[] Mr,int r_flag) /* Griewank's  */
	{
	    int i;
	    double s, p;

		shiftfunc(x, y, nx, Os);
		for (i=0; i<nx; i++)//shrink to the orginal search range
	    {
	        y[i]=y[i]*600.0/100.0;
	    }
		if (r_flag==1)
		rotatefunc(y, z, nx, Mr);
		else
	    for (i=0; i<nx; i++)
			z[i]=y[i];

		for (i=0; i<nx; i++)
			z[i] = z[i]*Math.pow(100.0,1.0*i/(nx-1)/2.0);


	    s = 0.0;
	    p = 1.0;
	    for (i=0; i<nx; i++)
	    {
	        s += z[i]*z[i];
	        p *= Math.cos(z[i]/Math.sqrt(1.0+i));
	    }
	    f = 1.0 + s/4000.0 - p;
	    
	    return f;
	}
	
	
	double rastrigin_func (double[] x, double f, int nx, double[] Os,double[] Mr,int r_flag) /* Rastrigin's  */
	{
	    int i;
		double alpha=10.0,beta=0.2;
		shiftfunc(x, y, nx, Os);
		for (i=0; i<nx; i++)//shrink to the orginal search range
	    {
	        y[i]=y[i]*5.12/100;
	    }

		if (r_flag==1)
		rotatefunc(y, z, nx, Mr);
		else
	    for (i=0; i<nx; i++)
			z[i]=y[i];

	    oszfunc (z, y, nx);
	    asyfunc (y, z, nx, beta);

	    if (r_flag==1){
			double[] t = new double[nx*nx];
			for(i=0;i<nx*nx;i++){
				t[i] = Mr[nx*nx+i];
			}
						rotatefunc(z, y, nx, t);
		}
		else
	    for (i=0; i<nx; i++)
			y[i]=z[i];

		for (i=0; i<nx; i++)
		{
			y[i]*=Math.pow(alpha,1.0*i/(nx-1)/2);
		}

		if (r_flag==1)
		rotatefunc(y, z, nx, Mr);
		else
	    for (i=0; i<nx; i++)
			z[i]=y[i];

	    f = 0.0;
	    for (i=0; i<nx; i++)
	    {
	        f += (z[i]*z[i] - 10.0*Math.cos(2.0*PI*z[i]) + 10.0);
	    }
	    
	    return f;
	}
	
	
	double step_rastrigin_func (double[] x, double f, int nx, double[] Os,double[] Mr,int r_flag) /* Noncontinuous Rastrigin's  */
	{
	    int i;
		double alpha=10.0,beta=0.2;
		shiftfunc(x, y, nx, Os);
		for (i=0; i<nx; i++)//shrink to the orginal search range
	    {
	        y[i]=y[i]*5.12/100;
	    }

		if (r_flag==1)
		rotatefunc(y, z, nx, Mr);
		else
	    for (i=0; i<nx; i++)
			z[i]=y[i];

	    for (i=0; i<nx; i++)
		{
			if (Math.abs(z[i])>0.5)
			z[i]=Math.floor(2*z[i]+0.5)/2;
		}

	    oszfunc (z, y, nx);
	    asyfunc (y, z, nx, beta);

	    if (r_flag==1){
			double[] t = new double[nx*nx];
			for(i=0;i<nx*nx;i++){
				t[i] = Mr[nx*nx+i];
			}
						rotatefunc(z, y, nx, t);
		}
		else
	    for (i=0; i<nx; i++)
			y[i]=z[i];

		for (i=0; i<nx; i++)
		{
			y[i]*=Math.pow(alpha,1.0*i/(nx-1)/2);
		}

		if (r_flag==1)
		rotatefunc(y, z, nx, Mr);
		else
	    for (i=0; i<nx; i++)
			z[i]=y[i];

	    f = 0.0;
	    for (i=0; i<nx; i++)
	    {
	        f += (z[i]*z[i] - 10.0*Math.cos(2.0*PI*z[i]) + 10.0);
	    }
	    
	    return f;
	}
	
	
	double schwefel_func (double[] x, double f, int nx, double[] Os,double[] Mr,int r_flag) /* Schwefel's  */
	{
	    int i;
		double tmp;
		shiftfunc(x, y, nx, Os);
		for (i=0; i<nx; i++)//shrink to the orginal search range
	    {
	        y[i]*=1000/100;
	    }
		if (r_flag==1)
		rotatefunc(y, z, nx, Mr);
		else
	    for (i=0; i<nx; i++)
			z[i]=y[i];

		for (i=0; i<nx; i++)
			y[i] = z[i]*Math.pow(10.0,1.0*i/(nx-1)/2.0);

		for (i=0; i<nx; i++)
			z[i] = y[i]+4.209687462275036e+002;
		
	    f=0;
	    for (i=0; i<nx; i++)
		{
			if (z[i]>500)
			{
				f-=(500.0-(z[i]%500))*Math.sin(Math.pow(500.0-(z[i]%500),0.5));
				tmp=(z[i]-500.0)/100;
				f+= tmp*tmp/nx;
			}
			else if (z[i]<-500)
			{
				f-=(-500.0+(Math.abs(z[i])%500))*Math.sin(Math.pow(500.0-(Math.abs(z[i])%500),0.5));
				tmp=(z[i]+500.0)/100;
				f+= tmp*tmp/nx;
			}
			else
				f-=z[i]*Math.sin(Math.pow(Math.abs(z[i]),0.5));
	    }
	    f=4.189828872724338e+002*nx+f;
	    
	    return f;
	}
	
	
	double katsuura_func (double[] x, double f, int nx, double []Os,double []Mr,int r_flag) /* Katsuura  */
	{
	    int i,j;
		double temp,tmp1,tmp2,tmp3;
		tmp3=Math.pow(1.0*nx,1.2);
		shiftfunc(x, y, nx, Os);
		for (i=0; i<nx; i++)//shrink to the orginal search range
	    {
	        y[i]*=5.0/100.0;
	    }
		if (r_flag==1)
		rotatefunc(y, z, nx, Mr);
		else
	    for (i=0; i<nx; i++)
			z[i]=y[i];

		for (i=0; i<nx; i++)
			z[i] *=Math.pow(100.0,1.0*i/(nx-1)/2.0);

		if (r_flag==1){
			double[] t = new double[nx*nx];
			for(i=0;i<nx*nx;i++){
				t[i] = Mr[nx*nx+i];
			}
						rotatefunc(z, y, nx, t);
		}
		else
	    for (i=0; i<nx; i++)
			y[i]=z[i];

	    f=1.0;
	    for (i=0; i<nx; i++)
		{
			temp=0.0;
			for (j=1; j<=32; j++)
			{
				tmp1=Math.pow(2.0,j);
				tmp2=tmp1*y[i];
				temp += Math.abs(tmp2-Math.floor(tmp2+0.5))/tmp1;
			}
			f *= Math.pow(1.0+(i+1)*temp,10.0/tmp3);
	    }
		tmp1=10.0/nx/nx;
	    f=f*tmp1-tmp1;
	    
	    return f;

	}
	
	
	double bi_rastrigin_func (double[] x, double f, int nx, double[] Os,double[] Mr,int r_flag) /* Lunacek Bi_rastrigin Function */
	{
	    int i;
		double mu0=2.5,d=1.0,s,mu1,tmp,tmp1,tmp2;
		double[] tmpx;
		tmpx=new double[nx];
		s=1.0-1.0/(2.0*Math.pow(nx+20.0,0.5)-8.2);
		mu1=-Math.pow((mu0*mu0-d)/s,0.5);

		shiftfunc(x, y, nx, Os);
		for (i=0; i<nx; i++)//shrink to the orginal search range
	    {
	        y[i]*=10.0/100.0;
	    }

		for (i = 0; i < nx; i++)
	    {
			tmpx[i]=2*y[i];
	        if (Os[i] < 0.)
	            tmpx[i] *= -1.;
	    }

		for (i=0; i<nx; i++)
		{
			z[i]=tmpx[i];
			tmpx[i] += mu0;
		}
		if (r_flag==1)
		rotatefunc(z, y, nx, Mr);
		else
	    for (i=0; i<nx; i++)
			y[i]=z[i];

		for (i=0; i<nx; i++)
			y[i] *=Math.pow(100.0,1.0*i/(nx-1)/2.0);
		if (r_flag==1){
			double[] t = new double[nx*nx];
			for(i=0;i<nx*nx;i++){
				t[i] = Mr[nx*nx+i];
			}
						rotatefunc(z, y, nx, t);
		}
		else
	    for (i=0; i<nx; i++)
			z[i]=y[i];

	    tmp1=0.0;tmp2=0.0;
	    for (i=0; i<nx; i++)
		{
			tmp = tmpx[i]-mu0;
			tmp1 += tmp*tmp;
			tmp = tmpx[i]-mu1;
			tmp2 += tmp*tmp;
	    }
		tmp2 *= s;
		tmp2 += d*nx;
		tmp=0;
		for (i=0; i<nx; i++)
		{
			tmp+=Math.cos(2.0*PI*z[i]);
	    }
		
		if(tmp1<tmp2)
			f = tmp1;
		else
			f = tmp2;
		f += 10.0*(nx-tmp);
		
		return f;
	}
	
	
	double grie_rosen_func (double[] x, double f, int nx, double[] Os,double[] Mr,int r_flag) /* Griewank-Rosenbrock  */
	{
	    int i;
	    double temp,tmp1,tmp2;

		shiftfunc(x, y, nx, Os);
		for (i=0; i<nx; i++)//shrink to the orginal search range
	    {
	        y[i]=y[i]*5/100;
	    }
		if (r_flag==1)
		rotatefunc(y, z, nx, Mr);
		else
	    for (i=0; i<nx; i++)
			z[i]=y[i];

		for (i=0; i<nx; i++)//shift to orgin
	    {
	        z[i]=y[i]+1;
	    }

	    f=0.0;
	    for (i=0; i<nx-1; i++)
	    {
			tmp1 = z[i]*z[i]-z[i+1];
			tmp2 = z[i]-1.0;
	        temp = 100.0*tmp1*tmp1 + tmp2*tmp2;
	         f += (temp*temp)/4000.0 - Math.cos(temp) + 1.0;
	    }
		tmp1 = z[nx-1]*z[nx-1]-z[0];
		tmp2 = z[nx-1]-1.0;
	    temp = 100.0*tmp1*tmp1 + tmp2*tmp2;;
	     f += (temp*temp)/4000.0 - Math.cos(temp) + 1.0 ;
	     
	     return f;
	}
	
	
	double escaffer6_func (double[] x, double f, int nx, double[] Os,double[] Mr,int r_flag) /* Expanded Scaffer¡¯s F6  */
	{
	    int i;
	    double temp1, temp2;
		shiftfunc(x, y, nx, Os);
		if (r_flag==1)
		rotatefunc(y, z, nx, Mr);
		else
	    for (i=0; i<nx; i++)
			z[i]=y[i];

		asyfunc (z, y, nx, 0.5);
		if (r_flag==1){
			double[] t = new double[nx*nx];
			for(i=0;i<nx*nx;i++){
				t[i] = Mr[nx*nx+i];
			}
						rotatefunc(z, y, nx, t);
		}
		else
	    for (i=0; i<nx; i++)
			z[i]=y[i];

	    f = 0.0;
	    for (i=0; i<nx-1; i++)
	    {
	        temp1 = Math.sin(Math.sqrt(z[i]*z[i]+z[i+1]*z[i+1]));
			temp1 =temp1*temp1;
	        temp2 = 1.0 + 0.001*(z[i]*z[i]+z[i+1]*z[i+1]);
	        f += 0.5 + (temp1-0.5)/(temp2*temp2);
	    }
	    temp1 = Math.sin(Math.sqrt(z[nx-1]*z[nx-1]+z[0]*z[0]));
		temp1 =temp1*temp1;
	    temp2 = 1.0 + 0.001*(z[nx-1]*z[nx-1]+z[0]*z[0]);
	    f += 0.5 + (temp1-0.5)/(temp2*temp2);
	    
	    return f;
	}
	
	
	double cf01 (double[] x, double f, int nx, double[] Os,double[] Mr,int r_flag) /* Composition Function 1 */
	{
		int i,j,cf_num=5;
		double[] fit = new double[5];// fit[5];
		double[] delta = {10, 20, 30, 40, 50};
		double[] bias = {0, 100, 200, 300, 400};
		
		double[] tOs = new double[nx];
		double[] tMr = new double[cf_num*nx*nx];
		
		i=0;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		fit[i]=rosenbrock_func(x,fit[i],nx,tOs,tMr,r_flag);
		fit[i]=10000*fit[i]/1e+4;
		
		i=1;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		fit[i]=dif_powers_func(x,fit[i],nx,tOs,tMr,r_flag);
		fit[i]=10000*fit[i]/1e+10;
		
		i=2;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		fit[i]=bent_cigar_func(x,fit[i],nx,tOs,tMr,r_flag);
		fit[i]=10000*fit[i]/1e+30;
		
		i=3;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		fit[i]=discus_func(x,fit[i],nx,tOs,tMr,r_flag);
		fit[i]=10000*fit[i]/1e+10;
		
		i=4;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		fit[i]=sphere_func(x,fit[i],nx,tOs,tMr,0);
		fit[i]=10000*fit[i]/1e+5;
	
	
		return cf_cal(x, f, nx, Os, delta,bias,fit,cf_num);
		
	}
	
	
	double cf02 (double[] x, double f, int nx, double[] Os,double[] Mr,int r_flag) /* Composition Function 2 */
	{
		int i,j,cf_num=3;
		double[] fit = new double[3];
		double[] delta = {20,20,20};
		double[] bias = {0, 100, 200};
		
		double[] tOs = new double[nx];
		double[] tMr = new double[cf_num*nx*nx];
		
		for(i=0;i<cf_num;i++)
		{
			for(j=0;j<nx;j++){
				tOs[j] = Os[i*nx+j];
			}
			for(j=0;j<nx*nx;j++){
				tMr[j] = Mr[i*nx*nx+j];
			}
			fit[i]=schwefel_func(x,fit[i],nx,tOs,tMr,r_flag);
		}
		
		return cf_cal(x, f, nx, Os, delta,bias,fit,cf_num);
	}
	
	
	double cf03 (double[] x, double f, int nx, double[] Os,double[]Mr,int r_flag) /* Composition Function 3 */
	{
		int i,j,cf_num=3;
		double[] fit=new double[3];
		double[] delta = {20,20,20};
		double[] bias = {0, 100, 200};
		
		double[] tOs = new double[nx];
		double[] tMr = new double[cf_num*nx*nx];
		
		for(i=0;i<cf_num;i++)
		{
			
			for(j=0;j<nx;j++){
				tOs[j] = Os[i*nx+j];
			}
			for(j=0;j<nx*nx;j++){
				tMr[j] = Mr[i*nx*nx+j];
			}
			fit[i]=schwefel_func(x,fit[i],nx,tOs,tMr,r_flag);
		}
		
		return cf_cal(x, f, nx, Os, delta,bias,fit,cf_num);
	}
	
	
	double cf04 (double[] x, double f, int nx, double[] Os,double[] Mr,int r_flag) /* Composition Function 4 */
	{
		int i,j,cf_num=3;
		double[] fit=new double[3];
		double[] delta = {20,20,20};
		double[] bias = {0, 100, 200};
		
		double[] tOs = new double[nx];
		double[] tMr = new double[cf_num*nx*nx];
			
		i=0;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<cf_num*nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
			
		}
		fit[i]=schwefel_func(x,fit[i],nx,tOs,tMr,r_flag);
		fit[i]=1000*fit[i]/(4e+3);
		
		i=1;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<cf_num*nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		
		}
		fit[i]=rastrigin_func(x,fit[i],nx,tOs,tMr,r_flag);
		fit[i]=1000*fit[i]/(1e+3);
		
		i=2;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<cf_num*nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		fit[i]=weierstrass_func(x,fit[i],nx,tOs,tMr,r_flag);
		fit[i]=1000*fit[i]/400;
		
		return cf_cal(x, f, nx, Os, delta,bias,fit,cf_num);
	}
	
	
	double cf05 (double[] x, double f, int nx, double[] Os,double[] Mr,int r_flag) /* Composition Function 4 */
	{
		int i,j,cf_num=3;
		double[] fit=new double[3];
		double[] delta = {10,30,50};
		double[] bias = {0, 100, 200};
		
		double[] tOs = new double[nx];
		double[] tMr = new double[cf_num*nx*nx];
		
		i=0;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<cf_num*nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		fit[i]=schwefel_func(x,fit[i],nx,tOs,tMr,r_flag);
		fit[i]=1000*fit[i]/4e+3;
		i=1;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<cf_num*nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		fit[i]=rastrigin_func(x,fit[i],nx,tOs,tMr,r_flag);
		fit[i]=1000*fit[i]/1e+3;
		i=2;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<cf_num*nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		fit[i]=weierstrass_func(x,fit[i],nx,tOs,tMr,r_flag);
		fit[i]=1000*fit[i]/400;
		
		return cf_cal(x, f, nx, Os, delta,bias,fit,cf_num);
	}

		
	double cf06 (double[] x, double f, int nx, double[] Os,double[] Mr,int r_flag) /* Composition Function 6 */
	{
		int i,j,cf_num=5;
		double[] fit=new double[5];
		double[] delta = {10,10,10,10,10};
		double[] bias = {0, 100, 200, 300, 400};
		
		double[] tOs = new double[nx];
		double[] tMr = new double[cf_num*nx*nx];
		
		i=0;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<cf_num*nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		fit[i]=schwefel_func(x,fit[i],nx,tOs,tMr,r_flag);
		fit[i]=1000*fit[i]/4e+3;
		i=1;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<cf_num*nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		fit[i]=rastrigin_func(x,fit[i],nx,tOs,tMr,r_flag);
		fit[i]=1000*fit[i]/1e+3;
		i=2;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<cf_num*nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		fit[i]=ellips_func(x,fit[i],nx,tOs,tMr,r_flag);
		fit[i]=1000*fit[i]/1e+10;
		i=3;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<cf_num*nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		fit[i]=weierstrass_func(x,fit[i],nx,tOs,tMr,r_flag);
		fit[i]=1000*fit[i]/400;
		i=4;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<cf_num*nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		fit[i]=griewank_func(x,fit[i],nx,tOs,tMr,r_flag);
		fit[i]=1000*fit[i]/100;
		
		return cf_cal(x, f, nx, Os, delta,bias,fit,cf_num);

	}

		
	double cf07 (double[] x, double f, int nx, double[] Os,double[] Mr,int r_flag) /* Composition Function 7 */
	{
		int i,j,cf_num=5;
		double[] fit=new double[5];
		double[] delta = {10,10,10,20,20};
		double[] bias = {0, 100, 200, 300, 400};
		
		double[] tOs = new double[nx];
		double[] tMr = new double[cf_num*nx*nx];
		
		i=0;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<cf_num*nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		fit[i]=griewank_func(x,fit[i],nx,tOs,tMr,r_flag);
		fit[i]=10000*fit[i]/100;
		
		i=1;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<cf_num*nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		fit[i]=rastrigin_func(x,fit[i],nx,tOs,tMr,r_flag);
		fit[i]=10000*fit[i]/1e+3;
		
		i=2;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<cf_num*nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		fit[i]=schwefel_func(x,fit[i],nx,tOs,tMr,r_flag);
		fit[i]=10000*fit[i]/4e+3;
	
		i=3;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<cf_num*nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		fit[i]=weierstrass_func(x,fit[i],nx,tOs,tMr,r_flag);
		fit[i]=10000*fit[i]/400;
		
		i=4;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<cf_num*nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		fit[i]=sphere_func(x,fit[i],nx,tOs,tMr,0);
		fit[i]=10000*fit[i]/1e+5;
	
		
		return cf_cal(x, f, nx, Os, delta,bias,fit,cf_num);
	}

		
	double cf08 (double[] x, double f, int nx, double[] Os,double[] Mr,int r_flag) /* Composition Function 8 */
	{
		int i,j,cf_num=5;
		double[] fit=new double[5];
		double[] delta = {10,20,30,40,50};
		double[] bias = {0, 100, 200, 300, 400};
		
		double[] tOs = new double[nx];
		double[] tMr = new double[cf_num*nx*nx];
		
		i=0;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<cf_num*nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		fit[i]=grie_rosen_func(x,fit[i],nx,tOs,tMr,r_flag);
		fit[i]=10000*fit[i]/4e+3;
		
		i=1;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<cf_num*nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		fit[i]=schaffer_F7_func(x,fit[i],nx,tOs,tMr,r_flag);
		fit[i]=10000*fit[i]/4e+6;
		
		i=2;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<cf_num*nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		fit[i]=schwefel_func(x,fit[i],nx,tOs,tMr,r_flag);
		fit[i]=10000*fit[i]/4e+3;
		
		i=3;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<cf_num*nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		fit[i]=escaffer6_func(x,fit[i],nx,tOs,tMr,r_flag);
		fit[i]=10000*fit[i]/2e+7;
		
		i=4;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<cf_num*nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		fit[i]=sphere_func(x,fit[i],nx,tOs,tMr,0);
		fit[i]=10000*fit[i]/1e+5;
		
		
		return cf_cal(x, f, nx, Os, delta,bias,fit,cf_num);
	}

			
	void shiftfunc (double[] x, double[] xshift, int nx,double[] Os)
	{
		int i;
	    for (i=0; i<nx; i++)
	    {
	        xshift[i]=x[i]-Os[i];
	    }
	}

	void rotatefunc (double[] x, double[] xrot, int nx,double[] Mr)
	{
		int i,j;
	    for (i=0; i<nx; i++)
	    {
	        xrot[i]=0;
				for (j=0; j<nx; j++)
				{
					xrot[i]=xrot[i]+x[j]*Mr[i*nx+j];
				}
	    }
	}
	
	void asyfunc (double[] x, double[] xasy, int nx, double beta)
	{
		int i;
	    for (i=0; i<nx; i++)
	    {
			if (x[i]>0)
	        xasy[i]=Math.pow(x[i],1.0+beta*i/(nx-1)*Math.pow(x[i],0.5));
	    }
	}
	
	
	void oszfunc (double[] x, double[] xosz, int nx)
	{
		int i,sx;
		double c1,c2,xx=0;
	    for (i=0; i<nx; i++)
	    {
			if (i==0||i==nx-1)
	        {
				if (x[i]!=0)
					xx=Math.log(Math.abs(x[i]));//xx=log(fabs(x[i]));
				if (x[i]>0)
				{	
					c1=10;
					c2=7.9;
				}
				else
				{
					c1=5.5;
					c2=3.1;
				}	
				if (x[i]>0)
					sx=1;
				else if (x[i]==0)
					sx=0;
				else
					sx=-1;
				xosz[i]=sx*Math.exp(xx+0.049*(Math.sin(c1*xx)+Math.sin(c2*xx)));
			}
			else
				xosz[i]=x[i];
	    }
	}
	
	
	double cf_cal(double[] x, double f, int nx, double[] Os,double[] delta,double[] bias,double[] fit, int cf_num)
	{
		int i,j;
			
		double[] w;
		double w_max=0,w_sum=0;
		w=new double[cf_num];
		for (i=0; i<cf_num; i++)
		{
			fit[i]+=bias[i];
			w[i]=0;
			for (j=0; j<nx; j++)
			{
				w[i]+=Math.pow(x[j]-Os[i*nx+j],2.0);
			}
			if (w[i]!=0)
				w[i]=Math.pow(1.0/w[i],0.5)*Math.exp(-w[i]/2.0/nx/Math.pow(delta[i],2.0));
			else
				w[i]=INF;
			if (w[i]>w_max)
				w_max=w[i];
		}

		for (i=0; i<cf_num; i++)
		{
			w_sum=w_sum+w[i];
		}
		if(w_max==0)
		{
			for (i=0; i<cf_num; i++)
				w[i]=1;
			w_sum=cf_num;
		}
		f = 0.0;
	    for (i=0; i<cf_num; i++)
	    {
			f=f+w[i]/w_sum*fit[i];
	    }
	    
	    return f;
		
	}
}

    


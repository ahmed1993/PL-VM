import java.util.ArrayList;

public class TestVM {

	public static void main(String[] args) {
		try {
			VM vm = new VM();
			vm.add("load x, 10");
			vm.add("load y, 5");
			vm.add("loop x");
			vm.add("inc y");
			vm.add("end");
			vm.add("goto AAA");
			vm.add("inc y");
			vm.add("AAA: inc y");
			vm.run();
			System.out.println(vm);
		} catch (Exception e) {
			System.out.println(e);
			// e.printStackTrace();
		}

		// for n+m
		try {
			VM vm2 = new VM();
			vm2.add("load x, 5");
			vm2.add("load y, 3");
			vm2.add("load answer, y");
			vm2.add("loop x");
			vm2.add("inc answer");
			vm2.add("end");
			vm2.run();
			System.out.println(vm2);
		} catch (Exception e) {
			System.out.println(e);
			// e.printStackTrace();
		}
		// for n*m, x = 3 and y = 5
		try {
			VM vm3 = new VM();
			vm3.add("load x, 5");
			vm3.add("load y, 3");
			vm3.add("load answer, 0");
			vm3.add("loop x");
			vm3.add("loop y");
			vm3.add("inc answer");
			vm3.add("end");
			vm3.add("end");
			vm3.run();
			System.out.println(vm3);
		} catch (Exception e) {
			System.out.println(e);
			// e.printStackTrace();
		}

		// for max(m,n)
		try{
			VM vm4 = new VM();
			vm4.add("load m, 5");
			vm4.add("load n, 8");
			//m-n
			vm4.add("load w, 0");
			vm4.add("load a, m");
			vm4.add("loop n");
			vm4.add("loop a");
			vm4.add("load v, w");
			vm4.add("inc w");
			vm4.add("end");
			vm4.add("load a, v");
			vm4.add("load w, 0");
			vm4.add("end");
			vm4.add("load diff, v");
			//n-m
			vm4.add("load w, 0");
			vm4.add("load a, n");
			vm4.add("loop m");
			vm4.add("loop a");
			vm4.add("load v, w");
			vm4.add("inc w");
			vm4.add("end");
			vm4.add("load a, v");
			vm4.add("load w, 0");
			vm4.add("end");
			vm4.add("load difftwo, v");
			//if m>n
			vm4.add("loop diff");
			vm4.add("load max, m");
			vm4.add("end");
			//if m<n
			vm4.add("loop difftwo");
			vm4.add("load max, n");
			vm4.add("end");
			vm4.run();
			System.out.println(vm4);
		}
		catch(Exception e){
			System.out.println(e);
			e.printStackTrace();
		}

		// for m-n
		try {
			VM vm5 = new VM();
			vm5.add("load m, 7");
			vm5.add("load n, 3");
			vm5.add("load w, 0");
			vm5.add("load a, m");
			vm5.add("loop n");
			vm5.add("loop a");
			vm5.add("load v, w");
			vm5.add("inc w");
			vm5.add("end");
			vm5.add("load a, v");
			vm5.add("load w, 0");
			vm5.add("end");
			vm5.add("load answer, v");
			vm5.run();
			System.out.println(vm5);
		} catch (Exception e) {
			System.out.println(e);
		}
		
		//double(x)=x+x
		try{
			VM vm6 = new VM();
			vm6.add("load x, 7");
			vm6.add("load answer, 0");
			vm6.add("loop x");
			vm6.add("inc answer");
			vm6.add("inc answer");
			vm6.add("end");
			vm6.run();
			System.out.println(vm6);
			
		}
		catch(Exception e){
			System.out.println(e);
		}
		
		//exp(x) = double(double(...(double(1)...)) aka 2^x
		try{
			VM vm7 = new VM();
			vm7.add("load x, 5");
			vm7.add("load result, 1");
			vm7.add("loop x");
			vm7.add("loop result");
			vm7.add("inc result");
			vm7.add("end");
			vm7.add("end");
			vm7.run();
			System.out.println(vm7);
			
		}
		catch(Exception e){
			System.out.println(e);
		}
		
	}
}
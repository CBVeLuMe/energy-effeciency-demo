import java.util.*;

import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.VmAllocationPolicySimple;
import org.cloudbus.cloudsim.VmSchedulerSpaceShared;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.provisioners.*;


/**
 * A testing Class of CloudSim for energy detector purpose.
 * One data center with four hosts
 *     Four 1000 MIPS cores
 *     8 GB RAM
 *     100 GB storage
 *     1 MPBS network bandwidth
 * One data center broker
 * 
 * @author CBVeLuMe
 */
public class CloudSimTest1 {
	public static void main(String[] args) {
		// TODO Initialize the package. Check the CloudSimExample1
		int numUser = 1;
		Calendar cal = Calendar.getInstance();
		boolean traceFlag = false;
		CloudSim.init(numUser, cal, traceFlag);
		// TODO Create the data center and define the the policies for the allocating and scheduling
		Datacenter datacenter = CreateDataCenter();
		// TODO Create the data center broker
		
		// TODO Create Cloudlets
		// TODO Create Virtual Machines and define the Procedure for task scheduling algorithm
		// TODO Implement the Power classes
		// TODO Test the demo and Print the result
	}

	private static Datacenter CreateDataCenter() {
		//Initialize the processing elements or CPUs
		List<Pe> peList = new ArrayList<Pe>();
		//One PE with 1000 Mips
		PeProvisionerSimple peProvisioner = new PeProvisionerSimple(1000);
		////Four 1000 MIPS PEs
		Pe core0 = new Pe(0, peProvisioner);
		Pe core1 = new Pe(1, peProvisioner);
		Pe core2 = new Pe(2, peProvisioner);
		Pe core3 = new Pe(3, peProvisioner);
		peList.add(core0);
		peList.add(core1);
		peList.add(core2);
		peList.add(core3);
		//Initialize the hosts
		List<Host> hostList = new ArrayList<Host>();
		//8 GB RAM
		int ram = 8000;
		//1 MPBS network bandwidth
		int bw = 1000;
		//100 GB storage
		long storage = 100000;
		Host host0 = new Host(0, new RamProvisionerSimple(ram), new BwProvisionerSimple(bw), storage, peList,
				new VmSchedulerSpaceShared(peList));
		Host host1 = new Host(1, new RamProvisionerSimple(ram), new BwProvisionerSimple(bw), storage, peList,
				new VmSchedulerSpaceShared(peList));
		Host host2 = new Host(2, new RamProvisionerSimple(ram), new BwProvisionerSimple(bw), storage, peList,
				new VmSchedulerSpaceShared(peList));
		Host host3 = new Host(3, new RamProvisionerSimple(ram), new BwProvisionerSimple(bw), storage, peList,
				new VmSchedulerSpaceShared(peList));
		hostList.add(host0);
		hostList.add(host1);
		hostList.add(host2);
		hostList.add(host3);
		//Initialize the data center
		String architecture = "x64";
		String os = "Kelly Linux";
		String vmm = "XEN";
		double timeZone = -4.0;
		double computeCostPerSec = 3.0;
		double costPerMem = 1.0;
		double costPerStorage = 0.05;
		double costPerBW = 0.10;
		DatacenterCharacteristics datacenterCharacteristics = new DatacenterCharacteristics(architecture, os, vmm,
				hostList, timeZone, computeCostPerSec, costPerMem, costPerStorage, costPerBW);
		LinkedList<Storage> SANstorage = new LinkedList<Storage>();
		Datacenter datacenter = null;
		try {
			datacenter = new Datacenter("datacenter0", datacenterCharacteristics,
					new VmAllocationPolicySimple(hostList), SANstorage, 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return datacenter;
	}

}

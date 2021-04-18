import java.util.*;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.CloudletScheduler;
import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelFull;
import org.cloudbus.cloudsim.Vm;
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
 * 40 Cloudlets (tasks/workload)
 *     4000 length instructions
 *     300 KB input file size
 *     400 KB output file size
 *     1 core CPU
 *     Utilization mode to full
 * 10 virtual machines
 *     20 GB storage disk
 *     2 GB RAM
 *     1 vCPU with 1000 MIPS CPU speed
 *     1000 KBITS/s bandwidth
 *     Timeshared scheduler for Cloudlets execution
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
		DatacenterBroker datacenterBroker = CreateDataCenterBroker();
		// TODO Create Cloudlet
		List<Cloudlet> cloudletList = new ArrayList<Cloudlet>();
		long cloudletLength = 40000;
		int pesNumber = 1;
		long cloudletFileSize = 300;
		long cloudletOutputSize = 400;
		UtilizationModel utilizationModelCpu = new UtilizationModelFull();
		UtilizationModel utilizationModelRam = new UtilizationModelFull();
		UtilizationModel utilizationModelBw = new UtilizationModelFull();
		for (int cloudletId = 0; cloudletId < 40; cloudletId++) {
			Cloudlet cloudlet = new Cloudlet(cloudletId, cloudletLength, pesNumber, cloudletFileSize, cloudletOutputSize,
					utilizationModelCpu, utilizationModelRam, utilizationModelBw);
			cloudlet.setUserId(datacenterBroker.getId());
			cloudletList.add(cloudlet);
		}
		// TODO Create Virtual Machines and define the Procedure for task scheduling algorithm
		List<Vm> vmList = new ArrayList<Vm>();
		int userId = 0;
		double mips = 1000;
		int numberOfPes = 1;
		int ram = 2000;
		long bandwidth = 1000;
		long diskSize = 20000;
		String vmm = "XEN";
		CloudletScheduler cloudletScheduler = new CloudletSchedulerTimeShared();
		for (int id = 0; id < 10; id++) {
			Vm virtualMachine = new Vm(id, datacenterBroker.getId(), mips, numberOfPes, ram, bandwidth, diskSize, vmm,
					cloudletScheduler);
			vmList.add(virtualMachine);
		}
		datacenterBroker.submitCloudletList(cloudletList);
		datacenterBroker.submitVmList(vmList);
		// TODO Implement the Power classes
		// TODO Test the demo and Print the result
	}

	private static Datacenter CreateDataCenter() {
		//Initialize the processing elements or CPUs
		List<Pe> peList = new ArrayList<Pe>();
		//One PE with 1000 Mips
		PeProvisionerSimple peProvisioner = new PeProvisionerSimple(1000);
		////Four 1000 MIPS PEs
		for (int id = 0; id < 4; id++) {
			Pe core = new Pe(id, peProvisioner);
			peList.add(core);
		}
		//Initialize the hosts
		List<Host> hostList = new ArrayList<Host>();
		//8 GB RAM
		int ram = 8000;
		//1 MPBS network bandwidth
		int bw = 1000;
		//100 GB storage
		long storage = 100000;
		for (int id = 0; id < 4; id++) {
			Host host = new Host(id, new RamProvisionerSimple(ram), new BwProvisionerSimple(bw), storage, peList,
					new VmSchedulerSpaceShared(peList));
			hostList.add(host);
		}
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
	
	private static DatacenterBroker CreateDataCenterBroker() {
		DatacenterBroker datacenterBroker = null;
		try {
			datacenterBroker = new DatacenterBroker("DatacenterBroker0");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return datacenterBroker;
	}

	private static Cloudlet CreateCloudlets() {
		Cloudlet cloudlet = null;
		return cloudlet;
	}
}

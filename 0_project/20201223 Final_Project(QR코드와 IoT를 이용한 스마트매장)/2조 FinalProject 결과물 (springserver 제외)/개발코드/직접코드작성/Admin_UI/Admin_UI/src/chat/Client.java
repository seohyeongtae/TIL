package chat;

import java.net.Socket;


public class Client {

	int port;
	String address;
	String id;
	Socket socket;
	String iotCmd;
	
	public Client() {
		
	}
	public Client(String adress, int port, String id) {
		this.address = adress;
		this.port = port;
		this.id = id;
	}
	
	public String getIotCmd() {
		return iotCmd;
	}
	public void setIotCmd(String iotCmd) {
		this.iotCmd = iotCmd;
	}
	
	
	@Override
	public String toString() {
		return "Client [port=" + port + ", address=" + address + ", id=" + id + ", socket=" + socket + ", iotCmd="
				+ iotCmd + "]";
	}
	

}
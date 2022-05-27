import java.math.BigInteger;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
public class BumperServant extends UnicastRemoteObject implements Bumper {
    BigInteger num = new BigInteger("0");
    public BumperServant() throws RemoteException {
    }

    public boolean bump() throws RemoteException {
        num = num.add(new BigInteger("1"));
        return true;
    }

    public BigInteger get() throws RemoteException {
        return num;
    }
}

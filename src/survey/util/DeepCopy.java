package survey.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class DeepCopy {
	 public static Serializable deepCopy(Serializable serializable){
		 ByteArrayOutputStream baos = null;
		ObjectOutputStream oos =null;
		 try {
			  baos = new ByteArrayOutputStream();
			  oos = new ObjectOutputStream(baos);
			  oos.writeObject(serializable);
			  baos.close();
			  oos.close();
			  
			  byte[] bytes = baos.toByteArray();
			  ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
			  ObjectInputStream ois = new ObjectInputStream(bais);
			  Serializable copy = (Serializable)ois.readObject(); 
			  bais.close();
			  ois.close();
			  return copy;
			  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 return  null;
	 }
}

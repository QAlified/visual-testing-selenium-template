package automation;

import java.io.IOException;

public class AutoIT {
	public AutoIT() {};
	public void lanzarAutoIT(String ejecutable){
        try {
            Runtime.getRuntime().exec(ejecutable);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}

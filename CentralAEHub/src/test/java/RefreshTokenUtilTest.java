import com.rohith.aeh.hub.util.date.AEHHubDateUtil;
import com.rohith.aeh.hub.util.token.RefreshTokenUtil;

public class RefreshTokenUtilTest {

	public static void main(String[] args) {
		
		long cu = System.currentTimeMillis();
		
		long time1 = AEHHubDateUtil.addHours(cu, 2);
		
		long time2 = AEHHubDateUtil.addDays(cu, 30);
		
		
		long secret = RefreshTokenUtil.calculateSecret(time2, time1);
		
		System.out.println(secret);
		
	}
}

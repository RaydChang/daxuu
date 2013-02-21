/**
 * @descrip:文件说明
 * @created on :2013/2/21
 * @author Administrator
 * @copyright: 寶(裕)元公司 Copyright © 2008 POU YUEN(YU YUEN) INDUSTRIAL(HOLDINGS) LTD.(All rights reserved) .
 *
 */
package Framework.CodeRobot;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @descrip:類功能概述
 * @created on :2013/2/21
 * @author Administrator
 * 
 * @UPDATE LOG:
 * @date :
 * @author:
 * @descrip:
 * 1.
 * 2.
 */
public class CoderTest {
	

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link Framework.CodeRobot.Coder#getGUID()}.
	 */
	@Test
	public void testGetGUID() {
		String actual="ced7dacc-67b1-4803-af11-0da3520d29c0";
		String ret = Coder.getCoder().getGUID();
		assertSame(ret.length(),  actual.length());
		//fail("Not yet implemented");
	}

}

package nuoman.com.framwork.network;

/**
 * 
 * com.mingzhi.samattendance.framework.exception.NetWorkConnectionException
 * @author 陈建辉 <br/>
 * create at 2013-6-18 上午11:08:11
 * @note 功能说明： 网络连接异常
 */
public class NetWorkConnectionException extends RuntimeException
{

	private static final long	serialVersionUID	= 1L;

	public NetWorkConnectionException(String message)
	{
		super(message);
		return;
	}

	public NetWorkConnectionException()
	{

	}

}

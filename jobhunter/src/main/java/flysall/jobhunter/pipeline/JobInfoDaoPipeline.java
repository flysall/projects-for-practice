package flysall.jobhunter.pipeline;

import org.springframework.stereotype.Component;
import com.flysall.jobhunter.dao.JobInfoDAO;
import com.flysall.jobhunter.model.LieTouJobInfo;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

import javax.annotation.Resource;

@Component("JobInfoDaoPipline")
public class JobInfoDaoPipeline implements PageModelPipeline<LieTouJobInfo>{
	@Resource
	private JobInfoDAO jobInfoDAO;
	
	@Override
	public void process(LieTouJobInfo lieTouJobInfo, Task task){
		jobInfoDAO.add(lieTouJobInfo);
	}
}

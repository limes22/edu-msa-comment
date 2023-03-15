package paasta.msa.common;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Class Name : CommonDAO.java
 * @Description : CommonDAO Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2020-12-22	 JaemooSong	PaaS-TA 환경으로 이전 및 수정
 *
 * @author JaemooSong
 *
 * @since 2009. 03.16
 * @version 1.0
 * @see
 *
 */
public class CommonDAO extends SqlSessionDaoSupport {

	@Autowired(required = false)
	@Override
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {

		super.setSqlSessionFactory(sqlSessionFactory);

	}

	@Autowired(required = false)
	@Override
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {

		super.setSqlSessionTemplate(sqlSessionTemplate);

	}
}

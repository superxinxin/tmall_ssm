package tmall.service.impl;
import tmall.mapper.PropertyMapper;
import tmall.pojo.Category;
import tmall.pojo.Product;
import tmall.pojo.Property;
import tmall.pojo.PropertyExample;
import tmall.service.CategoryService;
import tmall.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class PropertyServiceImpl implements PropertyService
{
	@Autowired
	PropertyMapper propertyMapper;
	@Override
	public void add(Property p)
	{
		propertyMapper.insert(p);
	}
	@Override
	public void delete(int id)
	{
		propertyMapper.deleteByPrimaryKey(id);
	}
	@Override
	public void update(Property p)
	{
		propertyMapper.updateByPrimaryKeySelective(p);
	}
	@Override
	public Property get(int id)
	{
		return propertyMapper.selectByPrimaryKey(id);
	}
	@Override
	public List list(int cid)
	{
		PropertyExample example = new PropertyExample();
		example.createCriteria().andCidEqualTo(cid);
		example.setOrderByClause("id desc");
		return propertyMapper.selectByExample(example);
	}
}
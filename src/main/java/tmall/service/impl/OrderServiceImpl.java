package tmall.service.impl;
import java.util.List;
import tmall.pojo.OrderItem;
import tmall.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tmall.mapper.OrderMapper;
import tmall.pojo.Order;
import tmall.pojo.OrderExample;
import tmall.pojo.User;
import tmall.service.OrderService;
import tmall.service.UserService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service
public class OrderServiceImpl implements OrderService
{
	@Autowired
	OrderMapper orderMapper;
	@Autowired
	UserService userService;
	@Autowired
	OrderItemService orderItemService;
	@Override
	public void add(Order c)
	{
		orderMapper.insert(c);
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = "Exception")
	public float add(Order o, List<OrderItem> ois)
	{
		float total = 0;
		add(o);
		if (false)
			throw new RuntimeException();
		for (OrderItem oi : ois)
		{
			oi.setOid(o.getId());
			orderItemService.update(oi);
			total += oi.getProduct().getPromotePrice() * oi.getNumber();
		}
		return total;
	}
	@Override
	public void delete(int id)
	{
		orderMapper.deleteByPrimaryKey(id);
	}
	@Override
	public void update(Order c)
	{
		orderMapper.updateByPrimaryKeySelective(c);
	}
	@Override
	public Order get(int id)
	{
		return orderMapper.selectByPrimaryKey(id);
	}
	public List<Order> list()
	{
		OrderExample example = new OrderExample();
		example.setOrderByClause("id desc");
		return orderMapper.selectByExample(example);
	}
	 @Override
	public List list(int uid, String excludedStatus) 
	{
	    OrderExample example =new OrderExample();
	    example.createCriteria().andUidEqualTo(uid).andStatusNotEqualTo(excludedStatus);
	    example.setOrderByClause("id desc");
	    return orderMapper.selectByExample(example);
	}
}
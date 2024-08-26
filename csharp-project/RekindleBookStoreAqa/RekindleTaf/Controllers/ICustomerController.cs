using System.Net;
using System.Threading.Tasks;
using RekindleTaf.Controllers.Crud;
using RekindleTaf.Model;
using RestSharp;

namespace RekindleTaf.Controllers;

public interface ICustomerController : ICrudController<Customer, Task<RestResponse>>
{
}
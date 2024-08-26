using System.Threading.Tasks;
using RekindleTaf.Controllers.Crud;
using RekindleTaf.Model;
using RestSharp;

namespace RekindleTaf.Controllers;

public interface IBookstoreProductController : ICrudController<Product, Task<RestResponse>>
{
}
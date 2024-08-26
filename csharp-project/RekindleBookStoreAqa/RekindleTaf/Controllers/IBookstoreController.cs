using System.Threading.Tasks;
using RekindleTaf.Controllers.Crud;
using RekindleTaf.Model;
using RestSharp;

namespace RekindleTaf.Controllers;

public interface IBookstoreController : ICrudController<Bookstore, Task<RestResponse>>
{
}
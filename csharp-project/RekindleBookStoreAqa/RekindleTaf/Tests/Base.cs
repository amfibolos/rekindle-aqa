using Allure.NUnit;
using NUnit.Extension.DependencyInjection;
using NUnit.Extension.DependencyInjection.Unity;
using NUnit.Framework;

[assembly: NUnitTypeInjectionFactory(typeof(UnityInjectionFactory))]
[assembly: NUnitTypeDiscoverer(typeof(IocRegistrarTypeDiscoverer))]
[assembly: Parallelizable(ParallelScope.Fixtures)]
[assembly: LevelOfParallelism(10)]
namespace RekindleTaf.Tests;

[AllureNUnit]
public class Base
{
    
}
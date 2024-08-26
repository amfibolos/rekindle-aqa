using NUnit.Extension.DependencyInjection.Abstractions;
using RekindleTaf.Configuration;
using RekindleTaf.Configuration.Rest;
using Unity;
using Unity.Lifetime;

namespace RekindleTaf.DI;

public class ConfigRegistrar : RegistrarBase<IUnityContainer>
{
    protected override void RegisterInternal(IUnityContainer container)
    {
        container.RegisterType<IEnvConfig, EnvConfig>(new SingletonLifetimeManager());
        container.RegisterType<IRestConfig, RestConfig>(new SingletonLifetimeManager());
    }
}
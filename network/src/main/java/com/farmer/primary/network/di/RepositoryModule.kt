package com.farmer.primary.network.di

import com.farmer.primary.network.repositorys.doctor.AvailableDoctorRepository
import com.farmer.primary.network.repositorys.doctor.AvailableDoctorRepositoryImp
import com.farmer.primary.network.repositorys.login.LoginRepository
import com.farmer.primary.network.repositorys.login.LoginRepositoryImp
import com.farmer.primary.network.repositorys.metadata.MetaDataRepository
import com.farmer.primary.network.repositorys.metadata.MetaDataRepositoryImp
import com.farmer.primary.network.repositorys.otp.OtpRepository
import com.farmer.primary.network.repositorys.otp.OtpRepositoryImp
import com.farmer.primary.network.repositorys.profile.ProfileRepository
import com.farmer.primary.network.repositorys.profile.ProfileRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dynamic.app.survey.data.dataSource.local.preferences.abstraction.DataStoreRepository
import dynamic.app.survey.data.dataSource.local.preferences.implementation.DataStoreRepositoryImpl
import javax.inject.Singleton

/**
 * Created by Pritom Dutta on 13/1/23.
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun provideMetaDataRepository(api: MetaDataRepositoryImp): MetaDataRepository

    @Binds
    @Singleton
    abstract fun provideLoginRepository(api: LoginRepositoryImp): LoginRepository

    @Binds
    @Singleton
    abstract fun provideOtpRepository(api: OtpRepositoryImp): OtpRepository

    @Binds
    @Singleton
    abstract fun provideDataStoreRepository(api: DataStoreRepositoryImpl): DataStoreRepository

    @Binds
    @Singleton
    abstract fun provideAvailableDoctorRepository(api: AvailableDoctorRepositoryImp): AvailableDoctorRepository

    @Binds
    @Singleton
    abstract fun provideProfileRepository(api: ProfileRepositoryImp): ProfileRepository
}
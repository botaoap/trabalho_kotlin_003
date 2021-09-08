package com.serasa.crud_room.di

import android.content.Context
import com.serasa.crud_room.database.AppDatabase
import com.serasa.crud_room.database.dao.AppointmentDAO
import com.serasa.crud_room.database.dao.CategoryDAO
import com.serasa.crud_room.database.dao.DoctorDAO
import com.serasa.crud_room.database.dao.PatientDAO
import com.serasa.crud_room.repository.AppointmentRepository
import com.serasa.crud_room.repository.CategoryRepository
import com.serasa.crud_room.repository.DoctorRepository
import com.serasa.crud_room.repository.PatientRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class HiltModule {

    @Provides
    fun providesDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    fun providesPatient(@ApplicationContext context: Context): PatientDAO {
        return AppDatabase.getDatabase(context).patientDAO()
    }

    @Provides
    fun providesDoctor(@ApplicationContext context: Context): DoctorDAO {
        return AppDatabase.getDatabase(context).doctorDAO()
    }

    @Provides
    fun providesCategory(@ApplicationContext context: Context): CategoryDAO {
        return AppDatabase.getDatabase(context).categoryDAO()
    }

    @Provides
    fun providesAppointment(@ApplicationContext context: Context): AppointmentDAO {
        return AppDatabase.getDatabase(context).appointmentDAO()
    }

    @Provides
    fun providesPatientRepository(patient: PatientDAO): PatientRepository = PatientRepository(patient)

    @Provides
    fun providesCategoryRepository(database: AppDatabase): CategoryRepository = CategoryRepository(database)

    @Provides
    fun providesDoctorRepository(doctorDAO: DoctorDAO): DoctorRepository = DoctorRepository(doctorDAO)

    @Provides
    fun provideAppointmentRepository(appointmentDAO: AppointmentDAO, categoryDAO: CategoryDAO): AppointmentRepository = AppointmentRepository(appointmentDAO, categoryDAO)
}
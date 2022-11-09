package com.example.soundlightrgb.di

import com.example.soundlightrgb.data.local.entity.ModeEntity
import com.example.soundlightrgb.data.mapper.ModeItemModelsToModeDaoMapper
import com.example.soundlightrgb.data.remote.request.VariableRequest
import com.example.soundlightrgb.data.remote.response.VariableResponse
import com.example.soundlightrgb.domain.generic.Transform
import com.example.soundlightrgb.domain.mapper.ValueToVariableRequestMapper
import com.example.soundlightrgb.domain.mapper.VariableResponseToValueMapper
import com.example.soundlightrgb.view.model.ModeItemModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class MapperModule {

    @Binds
    abstract fun bindVariableResponseToValueMapper(impl: VariableResponseToValueMapper): Transform<VariableResponse?, Double?>

    @Binds
    abstract fun bindValueToVariableRequestMapper(impl: ValueToVariableRequestMapper): Transform<Double, VariableRequest>

    @Binds
    abstract fun bindModeItemModelsToModeDaoMapper(imp: ModeItemModelsToModeDaoMapper): Transform<List<@JvmSuppressWildcards ModeItemModel>, List<@JvmSuppressWildcards ModeEntity>>
}
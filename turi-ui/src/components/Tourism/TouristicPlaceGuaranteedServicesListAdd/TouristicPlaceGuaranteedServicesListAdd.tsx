import React, { useState } from 'react'
import { useTranslation } from 'react-i18next'
import Input from '../../Shared/Controls/Input'
import { GuaranteedService } from '../../../types/touristicPlace.ts'
import { touristicPlaceService } from '../../../services/touristicPlaceService.ts'
import styles from './TouristicPlaceGuaranteedServicesListAdd.module.css'

interface Props {
    touristicPlaceId: number
    addGuaranteedServices: (guaranteedService: GuaranteedService) => void
}

const TouristicPlaceGuaranteedServicesListAdd = ({ touristicPlaceId, addGuaranteedServices }: Props) => {
    const { t } = useTranslation()
    const [service, setService] = useState<string>('')

    const handleAdd = async (event: React.MouseEvent) => {
        event.preventDefault()
        if (service !== '') {
            const guaranteedServiceResponse = await touristicPlaceService.createGuaranteedService({
                touristicPlaceId: touristicPlaceId,
                service: service
            })
            if (guaranteedServiceResponse.status === 200) {
                const guaranteedServiceData: GuaranteedService = await guaranteedServiceResponse.json()
                addGuaranteedServices(guaranteedServiceData)
                setService('')
            }
        }
    }

    return (
        <div className={styles.inputContainer}>
            <Input
                type={'text'}
                name={'new'}
                placeholder={t('tourism.touristic-place-guaranteed-services-placeholder')}
                value={service}
                onChange={(e) => setService(e.target.value)}
                required={false}
            />
            <button
                className={styles.create}
                onClick={(e) => handleAdd(e)}
            >
                {t('tourism.touristic-place-guaranteed-services-add')}
            </button>
        </div>
    )
}

export default TouristicPlaceGuaranteedServicesListAdd

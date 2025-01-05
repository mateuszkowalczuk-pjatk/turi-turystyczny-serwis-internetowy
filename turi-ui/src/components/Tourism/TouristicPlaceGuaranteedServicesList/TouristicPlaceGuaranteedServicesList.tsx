import React from 'react'
import { useTranslation } from 'react-i18next'
import { GuaranteedService } from '../../../types/touristicPlace.ts'
import { touristicPlaceService } from '../../../services/touristicPlaceService.ts'
import styles from './TouristicPlaceGuaranteedServicesList.module.css'

interface Props {
    guaranteedServices: GuaranteedService[]
    removeGuaranteedServices?: (guaranteedServiceId: number) => void
}

const TouristicPlaceGuaranteedServicesList = ({ guaranteedServices, removeGuaranteedServices }: Props) => {
    const { t } = useTranslation()

    const handleDelete = async (guaranteedService: GuaranteedService, event: React.MouseEvent) => {
        event.preventDefault()
        if (guaranteedService.guaranteedServiceId) {
            const guaranteedServiceResponse = await touristicPlaceService.deleteGuaranteedService(
                guaranteedService.guaranteedServiceId
            )
            if (guaranteedServiceResponse.status === 200 && removeGuaranteedServices) {
                removeGuaranteedServices(guaranteedService.guaranteedServiceId)
            }
        }
    }

    return (
        <div className={styles.list}>
            {guaranteedServices.map((guaranteedService, index) => (
                <div
                    key={index}
                    className={styles.item}
                >
                    <span>{guaranteedService.service}</span>
                    {removeGuaranteedServices && (
                        <button
                            className={styles.delete}
                            onClick={(e) => handleDelete(guaranteedService, e)}
                        >
                            {t('tourism.touristic-place-guaranteed-services-delete')}
                        </button>
                    )}
                </div>
            ))}
        </div>
    )
}

export default TouristicPlaceGuaranteedServicesList

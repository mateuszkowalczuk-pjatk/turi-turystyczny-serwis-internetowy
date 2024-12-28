import { useTranslation } from 'react-i18next'
import { StayInformation } from '../../../types/stay.ts'
import { stayService } from '../../../services/stayService.ts'
import styles from './TourismStayInformationsList.module.css'

interface TourismStayInformationsListProps {
    stayInformations: StayInformation[]
    removeStayInformation: (information: string) => void
    modify?: boolean
}

const TourismStayInformationsList = ({
    stayInformations,
    removeStayInformation,
    modify
}: TourismStayInformationsListProps) => {
    const { t } = useTranslation()

    const handleRemove = async (stayInformation: StayInformation) => {
        if (modify && stayInformation.stayInformationId)
            await stayService.deleteStayInformation(stayInformation.stayInformationId)
    }

    return (
        <div className={styles.list}>
            {stayInformations.map((stayInformation, index) => (
                <div
                    key={index}
                    className={styles.item}
                >
                    <span>{stayInformation.information}</span>
                    <button
                        className={styles.delete}
                        type="button"
                        onClick={() => {
                            handleRemove(stayInformation).catch((error) => error)
                            removeStayInformation(stayInformation.information)
                        }}
                    >
                        {t('tourism.touristic-place-guaranteed-services-delete')}
                    </button>
                </div>
            ))}
        </div>
    )
}

export default TourismStayInformationsList

import { useTranslation } from 'react-i18next'
import PersonalLabel from '../../Shared/Personal/PersonalLabel'
import TourismStayInformationsList from '../TourismStayInformationsList'
import TourismStayInformationsCreate from '../TourismStayInformationsCreate'
import { StayInformation } from '../../../types/stay.ts'
import styles from './TourismStayInformations.module.css'

interface Props {
    stayInformations: StayInformation[]
    createStayInformation: (stayInformation: StayInformation) => void
    removeStayInformation: (information: string) => void
    stayId?: number
    modify?: boolean
}

const TourismStayInformations = ({
    stayInformations,
    createStayInformation,
    removeStayInformation,
    stayId,
    modify
}: Props) => {
    const { t } = useTranslation()

    return (
        <div className={styles.informations}>
            <PersonalLabel text={t('tourism.touristic-place-stay-offer-informations')} />
            <TourismStayInformationsList
                stayInformations={stayInformations}
                removeStayInformation={removeStayInformation}
                modify={modify}
            />
            <TourismStayInformationsCreate
                createStayInformation={createStayInformation}
                stayId={stayId}
                modify={modify}
            />
        </div>
    )
}

export default TourismStayInformations

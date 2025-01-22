import { useHooks } from '../../../hooks/useHooks.ts'
import Label from '../../Shared/Controls/Label'
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
    const { t } = useHooks()

    return (
        <div className={styles.informations}>
            <Label text={t('tourism.touristic-place-stay-offer-informations')} />
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

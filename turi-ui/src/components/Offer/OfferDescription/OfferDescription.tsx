import { useTranslation } from 'react-i18next'
import TouristicPlaceGuaranteedServicesList from '../../Tourism/TouristicPlaceGuaranteedServicesList'
import PersonalLabel from '../../Shared/Personal/PersonalLabel'
import TextRegular from '../../Shared/Controls/Text/TextRegular'
import { GuaranteedService } from '../../../types/touristicPlace.ts'
import styles from './OfferDescription.module.css'

interface OfferDescriptionProps {
    description: string | undefined
    guaranteedServices: GuaranteedService[]
}

const OfferDescription = ({ description, guaranteedServices }: OfferDescriptionProps) => {
    const { t } = useTranslation()

    return (
        <div className={styles.description}>
            <div className={styles.text}>
                <TextRegular text={description || ''} />
            </div>
            <div className={styles.services}>
                <PersonalLabel text={t('tourism.touristic-place-guaranteed-services')} />
                <TouristicPlaceGuaranteedServicesList guaranteedServices={guaranteedServices} />
            </div>
        </div>
    )
}

export default OfferDescription

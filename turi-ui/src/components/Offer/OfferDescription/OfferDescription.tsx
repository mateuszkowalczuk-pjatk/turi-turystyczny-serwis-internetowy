import { useHooks } from '../../../hooks/shared/useHooks.ts'
import Label from '../../Shared/Controls/Label'
import TextRegular from '../../Shared/Controls/Text/TextRegular'
import TouristicPlaceGuaranteedServicesList from '../../Tourism/TouristicPlaceGuaranteedServicesList'
import { GuaranteedService } from '../../../types/touristicPlace.ts'
import styles from './OfferDescription.module.css'

interface OfferDescriptionProps {
    description: string | undefined
    guaranteedServices: GuaranteedService[]
}

const OfferDescription = ({ description, guaranteedServices }: OfferDescriptionProps) => {
    const { t } = useHooks()

    return (
        <div className={styles.description}>
            <div className={styles.text}>
                <TextRegular text={description || ''} />
            </div>
            <div className={styles.services}>
                <Label text={t('tourism.touristic-place-guaranteed-services')} />
                <TouristicPlaceGuaranteedServicesList guaranteedServices={guaranteedServices} />
            </div>
        </div>
    )
}

export default OfferDescription

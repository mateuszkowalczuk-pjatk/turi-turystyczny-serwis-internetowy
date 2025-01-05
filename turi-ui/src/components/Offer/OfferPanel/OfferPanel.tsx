import OfferMain from '../OfferMain'
import OfferDescription from '../OfferDescription'
import OfferServices from '../OfferServices'
import OfferInformation from '../OfferInformation'
import OfferOwner from '../OfferOwner'
import OfferOpinion from '../OfferOpinion'
import { Offer } from '../../../types/offer.ts'
import styles from './OfferPanel.module.css'

const OfferPanel = ({ offer }: { offer: Offer }) => {
    return (
        <div className={styles.panel}>
            <OfferMain touristicPlace={offer.touristicPlace} />
            <OfferDescription
                description={offer.touristicPlace.description}
                guaranteedServices={offer.guaranteedServices}
            />
            <OfferServices
                dateFrom={''}
                dateTo={''}
                stays={offer.stays}
                attractions={offer.attractions}
            />
            <OfferInformation touristicPlace={offer.touristicPlace} />
            <OfferOwner
                premiumId={offer.touristicPlace.premiumId}
                description={offer.touristicPlace.ownerDescription || ''}
            />
            <OfferOpinion />
        </div>
    )
}

export default OfferPanel

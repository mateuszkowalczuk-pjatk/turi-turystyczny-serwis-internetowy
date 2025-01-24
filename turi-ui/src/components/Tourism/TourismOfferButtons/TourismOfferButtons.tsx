import { useHooks } from '../../../hooks/shared/useHooks.ts'
import { GreenButton, GreyButton } from '../../Shared/Controls/Button'
import Error from '../../Shared/Error'
import { stayService } from '../../../services/stayService.ts'
import { attractionService } from '../../../services/attractionService.ts'
import styles from './TourismOfferButtons.module.css'

interface Props {
    modify: boolean
    id?: number
    mode?: 'stay' | 'attraction'
    error?: string | null
}

const TourismOfferButtons = ({ modify, id, mode, error }: Props) => {
    const { t, navigate } = useHooks()

    const handleDelete = async () => {
        if (id && mode === 'stay') {
            await stayService.delete(id)
            navigate('/tourism')
        } else if (id && mode === 'attraction') {
            await attractionService.delete(id)
            navigate('/tourism')
        }
    }

    return (
        <div className={styles.buttons}>
            {error && <Error error={error} />}
            {modify && (
                <GreyButton
                    text={t('tourism.touristic-place-stay-offer-delete')}
                    onClick={handleDelete}
                />
            )}
            {modify && (
                <GreenButton
                    text={t('tourism.touristic-place-stay-offer-save')}
                    type={'submit'}
                />
            )}
            {!modify && (
                <GreenButton
                    text={t('tourism.touristic-place-stay-offer-create')}
                    type={'submit'}
                />
            )}
        </div>
    )
}

export default TourismOfferButtons

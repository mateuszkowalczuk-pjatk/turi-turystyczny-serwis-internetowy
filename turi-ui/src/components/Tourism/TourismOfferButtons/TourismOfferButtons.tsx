import { useNavigate } from 'react-router-dom'
import { GreenButton, GreyButton } from '../../Shared/Controls/Button'
import AuthError from '../../Auth/AuthError'
import { useTranslation } from 'react-i18next'
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
    const navigate = useNavigate()
    const { t } = useTranslation()

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
            {error && <AuthError error={error} />}
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

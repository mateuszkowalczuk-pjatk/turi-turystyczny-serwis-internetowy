import { useTranslation } from 'react-i18next'
import { GreenButton } from '../../Shared/Controls/Button'
import AuthError from '../../Auth/AuthError'
import styles from './TourismTouristicPlaceSave.module.css'

const TourismTouristicPlaceSave = ({ error }: { error: string | null }) => {
    const { t } = useTranslation()

    return (
        <div className={styles.save}>
            {error && <AuthError error={error} />}
            <GreenButton
                type={'submit'}
                text={t('tourism.touristic-place-save')}
            />
        </div>
    )
}

export default TourismTouristicPlaceSave

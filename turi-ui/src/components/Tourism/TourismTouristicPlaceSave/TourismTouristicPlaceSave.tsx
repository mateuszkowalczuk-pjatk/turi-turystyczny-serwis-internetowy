import { useHooks } from '../../../hooks/shared/useHooks.ts'
import { GreenButton } from '../../Shared/Controls/Button'
import Error from '../../Shared/Error'
import styles from './TourismTouristicPlaceSave.module.css'

const TourismTouristicPlaceSave = ({ error }: { error: string | null }) => {
    const { t } = useHooks()

    return (
        <div className={styles.save}>
            {error && <Error error={error} />}
            <GreenButton
                type={'submit'}
                text={t('tourism.touristic-place-save')}
            />
        </div>
    )
}

export default TourismTouristicPlaceSave

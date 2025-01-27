import MainPropositionPanel from '../MainPropositionPanel'
import { TouristicPlaceType } from '../../../../types/touristicPlace.ts'
import { AttractionType } from '../../../../types/attraction.ts'
import { SearchMode } from '../../../../types/offer.ts'
import styles from './MainPropositionPanels.module.css'

interface Props {
    firstText: string
    firstTouristicPlaceType?: TouristicPlaceType
    firstAttractionType?: AttractionType
    secondText: string
    secondTouristicPlaceType?: TouristicPlaceType
    secondAttractionType?: AttractionType
    thirdText: string
    thirdTouristicPlaceType?: TouristicPlaceType
    thirdAttractionType?: AttractionType
    fourthText: string
    fourthTouristicPlaceType?: TouristicPlaceType
    fourthAttractionType?: AttractionType
    fifthText: string
    fifthTouristicPlaceType?: TouristicPlaceType
    fifthAttractionType?: AttractionType
    sixthText: string
    sixthTouristicPlaceType?: TouristicPlaceType
    sixthAttractionType?: AttractionType
    mode: SearchMode
}

const MainPropositionPanels = ({
    firstText,
    firstTouristicPlaceType,
    firstAttractionType,
    secondText,
    secondTouristicPlaceType,
    secondAttractionType,
    thirdText,
    thirdTouristicPlaceType,
    thirdAttractionType,
    fourthText,
    fourthTouristicPlaceType,
    fourthAttractionType,
    fifthText,
    fifthTouristicPlaceType,
    fifthAttractionType,
    sixthText,
    sixthTouristicPlaceType,
    sixthAttractionType,
    mode
}: Props) => {
    return (
        <div className={styles.panels}>
            <MainPropositionPanel
                text={firstText}
                mode={mode}
                touristicPlaceType={firstTouristicPlaceType}
                attractionType={firstAttractionType}
            />
            <MainPropositionPanel
                text={secondText}
                mode={mode}
                touristicPlaceType={secondTouristicPlaceType}
                attractionType={secondAttractionType}
            />
            <MainPropositionPanel
                text={thirdText}
                mode={mode}
                touristicPlaceType={thirdTouristicPlaceType}
                attractionType={thirdAttractionType}
            />
            <MainPropositionPanel
                text={fourthText}
                mode={mode}
                touristicPlaceType={fourthTouristicPlaceType}
                attractionType={fourthAttractionType}
            />
            <MainPropositionPanel
                text={fifthText}
                mode={mode}
                touristicPlaceType={fifthTouristicPlaceType}
                attractionType={fifthAttractionType}
            />
            <MainPropositionPanel
                text={sixthText}
                mode={mode}
                touristicPlaceType={sixthTouristicPlaceType}
                attractionType={sixthAttractionType}
            />
        </div>
    )
}

export default MainPropositionPanels
